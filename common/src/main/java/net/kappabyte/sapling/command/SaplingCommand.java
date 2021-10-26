package net.kappabyte.sapling.command;

public abstract class SaplingCommand {

    public abstract void register();

    protected final void addSyntax(Argument... args) {

    }

}
