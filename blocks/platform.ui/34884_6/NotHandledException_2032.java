package org.eclipse.ui.commands;

@Deprecated
@SuppressWarnings("all")
public final class NotDefinedException extends CommandException {

    private static final long serialVersionUID = 3257572788998124596L;

	@Deprecated
    public NotDefinedException(String s) {
        super(s);
    }

	@Deprecated
    public NotDefinedException(
            final org.eclipse.core.commands.common.NotDefinedException e) {
        super(e.getMessage(), e);
    }
}
