package net.kappabyte.sapling.platform.fabric.mixin;

import net.kappabyte.sapling.SaplingAPI;
import net.kappabyte.sapling.core.SaplingPlayer;
import net.kappabyte.sapling.gui.GUIComponent.ClickType;
import net.kappabyte.sapling.gui.GUIManager;
import net.kappabyte.sapling.platform.fabric.gui.GUIHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class GUIEventMixin {
    private ScreenHandler previousScreen = null;

    @Shadow
    public ServerPlayerEntity player;

    @Shadow
    public abstract void sendPacket(Packet<?> packet);

    @Inject(method = "onClickSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;updateLastActionTime()V", shift = At.Shift.AFTER), cancellable = true)
    private void handleGuiClicks(ClickSlotC2SPacket packet, CallbackInfo ci) {
        if (this.player.currentScreenHandler instanceof GUIHandler) {
            try {
                GUIHandler handler = (GUIHandler) this.player.currentScreenHandler;

                int slot = packet.getSlot();
                int button = packet.getButton();
                ClickType type = toClickType(packet.getActionType(), button);
                SaplingAPI.getInstance().getGUIManager().handleInventoryClick(slot, handler.getPlayer().getUniqueID(), type, handler.getGUI());

                if (slot >= 0 && slot < handler.getInventory().size()) {
                    this.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), slot, handler.getSlot(slot).getStack()));
                }
                this.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(-1, handler.nextRevision(), -1, this.player.currentScreenHandler.getCursorStack()));

                if (packet.getActionType().equals(SlotActionType.SWAP)) {
                    this.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), getValue(packet.getActionType(), button) + handler.slots.size() - 10, handler.getSlot(getValue(packet.getActionType(), button) + handler.slots.size() - 10).getStack()));
                } else if (type == ClickType.DOUBLE_CLICK || type == ClickType.SHIFT_CLICK || (packet.getActionType().equals(SlotActionType.QUICK_CRAFT) && (button == 2 || button == 6 || button == 10))) {
                    this.sendPacket(new InventoryS2CPacket(handler.syncId, handler.nextRevision(), handler.getStacks(), this.player.currentScreenHandler.getCursorStack()));
                }
            } catch (Exception e) {
                e.printStackTrace();
                ci.cancel();
            }

            ci.cancel();
        }
    }

    @Inject(method = "onClickSlot", at = @At("TAIL"))
    private void resyncGui(ClickSlotC2SPacket packet, CallbackInfo ci) {
        if (this.player.currentScreenHandler instanceof GUIHandler) {
            try {
                GUIHandler handler = (GUIHandler) this.player.currentScreenHandler;

                int button = packet.getButton();
                ClickType type = toClickType(packet.getActionType(), button);

                if (type == ClickType.DOUBLE_CLICK || (packet.getActionType().equals(SlotActionType.QUICK_CRAFT) && (button == 2 || button == 6 || button == 10)) || type == ClickType.SHIFT_CLICK) {
                    this.sendPacket(new InventoryS2CPacket(handler.syncId, handler.nextRevision(), handler.getStacks(), handler.getCursorStack()));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Inject(method = "onCloseHandledScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;closeScreenHandler()V", shift = At.Shift.BEFORE))
    private void storeScreenHandler(CloseHandledScreenC2SPacket packet, CallbackInfo info) {
        if (this.player.currentScreenHandler instanceof GUIHandler) {
            this.previousScreen = this.player.currentScreenHandler;
        }
    }

    @Inject(method = "onCloseHandledScreen", at = @At("TAIL"))
    private void executeClosing(CloseHandledScreenC2SPacket packet, CallbackInfo info) {
        try {
            if (this.previousScreen != null) {
                if (this.previousScreen instanceof GUIHandler) {
                    GUIHandler handler = (GUIHandler) this.previousScreen;
                    //TODO
                    SaplingAPI.getInstance().getGUIManager().handleClose(SaplingPlayer.getPlayerFromNativePlayer(this.player), handler.getGUI());
                    //((GUIHandler) this.previousScreen).getGUI().close(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.previousScreen = null;
    }

    private static ClickType toClickType(SlotActionType type, int button) {
        return switch (type) {
            case PICKUP -> button == 0 ? ClickType.LEFT_CLICK : ClickType.RIGHT_CLICK;
            case QUICK_MOVE -> ClickType.SHIFT_CLICK;
            case CLONE -> ClickType.MIDDLE_CLICK;
            case PICKUP_ALL -> ClickType.DOUBLE_CLICK;
            case QUICK_CRAFT -> button >= 0 && button <= 2 ? ClickType.LEFT_DRAGGING : ClickType.RIGHT_DRAGGING;
            case THROW -> ClickType.DROP;
            case SWAP -> ClickType.LEFT_CLICK;
        };
    }

    private static int getValue(SlotActionType action, int button) {
        return switch (action) {
            case PICKUP -> -1;
            case QUICK_MOVE -> -1;
            case SWAP -> button + 1;
            case CLONE -> -1;
            case THROW -> -1;
            case QUICK_CRAFT -> switch (button) {
                    case 0 -> 0;
                    case 1 -> 1;
                    case 2 -> 2;
                    case 4 -> 0;
                    case 5 -> 1;
                    case 6 -> 2;
                    case 8 -> 0;
                    case 9 -> 1;
                    case 10 -> 2;
                    default -> -1;
                };
            case PICKUP_ALL -> -1;
        };
    }
}
