
package org.eclipse.jgit.errors;

import org.eclipse.jgit.internal.storage.file.Pack;

public class StoredPackRepresentationNotAvailableException extends Exception {
    private static final long serialVersionUID = 1L;

    public StoredPackRepresentationNotAvailableException(Pack pack
                                                         Throwable cause) {
        super(cause);
    }
}
