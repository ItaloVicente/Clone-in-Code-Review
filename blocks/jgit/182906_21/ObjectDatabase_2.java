package org.eclipse.jgit.internal.storage.pack;

import java.text.MessageFormat;
import org.eclipse.jgit.internal.storage.file.Pack;
import org.eclipse.jgit.internal.JGitText;

import java.io.IOException;

public class StaleFileHandleOnPackfile extends IOException {
    private static final long serialVersionUID = 1L;

    private final Pack pack;

    public StaleFileHandleOnPackfile(Pack missingPack) {
        super(MessageFormat.format(JGitText.get().packWasDeleted
				missingPack.getPackFile().getAbsolutePath()));
        pack = missingPack;
    }

    public Pack getPack() {
        return pack;
    }

    public IOException getOriginalException() {
        return (IOException) this.getCause();
    }
}
