package org.eclipse.jgit.internal.storage.pack;

import org.eclipse.jgit.internal.storage.file.Pack;

import java.io.IOException;

public class PackNotFoundException extends IOException {
    private static final long serialVersionUID = 1L;

    private final Pack pack;
    private final IOException originalException;

    public PackNotFoundException(Pack missingPack
        super("Missing pack file " + missingPack.getPackName());
        originalException = ioe;
        pack = missingPack;
    }

    public Pack getPack() {
        return pack;
    }

    public IOException getOriginalException() {
        return originalException;
    }
}
