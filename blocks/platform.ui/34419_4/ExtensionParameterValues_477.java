package org.eclipse.ui.commands;

@Deprecated
@SuppressWarnings("all")
public final class ExecutionException extends CommandException {

    private static final long serialVersionUID = 3258130262767448120L;

    public ExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExecutionException(final org.eclipse.core.commands.ExecutionException e) {
        super(e.getMessage(), e);
    }
}
