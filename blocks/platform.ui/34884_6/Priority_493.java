package org.eclipse.ui.commands;

@Deprecated
@SuppressWarnings("all")
public final class NotHandledException extends CommandException {

    private static final long serialVersionUID = 3256446914827726904L;

	@Deprecated
    public NotHandledException(String s) {
        super(s);
    }

	@Deprecated
    public NotHandledException(final org.eclipse.core.commands.NotHandledException e) {
        super(e.getMessage(), e);
    }
}
