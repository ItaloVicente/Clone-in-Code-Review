
package org.eclipse.jgit.niofs.internal.op.exceptions;

public class GitException extends RuntimeException {

    public GitException(final String message) {
        super(message);
    }

    public GitException(final String message
                        final Throwable t) {
        super(message
              t);
    }
}
