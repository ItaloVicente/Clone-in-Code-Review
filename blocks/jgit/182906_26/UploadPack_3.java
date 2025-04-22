
package org.eclipse.jgit.transport;

import java.io.IOException;

public class SendPackException extends IOException {
    private static final long serialVersionUID = 1L;

    public SendPackException(Throwable why) {
        initCause(why);
    }

    @Override
    public synchronized Throwable fillInStackTrace() { return this; }
}
