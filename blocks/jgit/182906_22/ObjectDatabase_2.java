
package org.eclipse.jgit.internal.storage.pack;

import org.eclipse.jgit.internal.storage.file.Pack;
import org.eclipse.jgit.transport.UploadPackSendPackException;

import java.io.IOException;

public class StaleFileHandleOnPackfile extends UploadPackSendPackException {
    private static final long serialVersionUID = 1L;

    private final Pack pack;

    public StaleFileHandleOnPackfile(Throwable why
        super(why);
        this.pack = missingPack;
    }

    public Pack getPack() {
        return pack;
    }

    public IOException getOriginalException() {
        return (IOException) this.getCause();
    }
}
