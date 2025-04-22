package org.eclipse.jgit.niofs;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;

public final class JGitFileSystemBuilder {

    private static final JGitFileSystemProvider PROVIDER = new JGitFileSystemProvider();
    private static final Map<String

    private JGitFileSystemBuilder() {
        DEFAULT_OPTIONS.put("init"
    }

    public static FileSystem newFileSystem(final String repoName) throws IOException {
                                      DEFAULT_OPTIONS);
    }
}
