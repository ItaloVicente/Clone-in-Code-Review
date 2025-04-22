
package org.eclipse.jgit.internal.storage.pack;

import org.eclipse.jgit.transport.TriggerRefreshPackListException;

import java.io.IOException;

public class StaleFileHandleOnPackfile extends TriggerRefreshPackListException {
    private static final long serialVersionUID = 1L;

    public StaleFileHandleOnPackfile(Throwable why) {
        super(why);
    }

    public IOException getOriginalException() {
        return (IOException) this.getCause();
    }
}
