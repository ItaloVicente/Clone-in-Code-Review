
package org.eclipse.jgit.niofs.internal;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Set;

import org.junit.Test;

import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JGitFileSystemImplProviderUnsupportedOpTest extends AbstractTestInfra {

    @Test
    public void testNewFileSystemUnsupportedOp() throws IOException {

        final FileSystem fs = provider.newFileSystem(newRepo
                                                     EMPTY_ENV);

        final Path path = JGitPathImpl.create((JGitFileSystem) fs
                                              ""
                                              "repo2-name"
                                              false);

        assertThatThrownBy(() -> provider.newFileSystem(path
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void testNewFileChannelUnsupportedOp() throws IOException {

        provider.newFileSystem(newRepo
                               EMPTY_ENV);


        final Set<? extends OpenOption> options = emptySet();
        assertThatThrownBy(() -> provider.newFileChannel(path
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void testNewAsynchronousFileChannelUnsupportedOp() throws IOException {

        provider.newFileSystem(newRepo
                               EMPTY_ENV);


        final Set<? extends OpenOption> options = emptySet();
        assertThatThrownBy(() -> provider.newAsynchronousFileChannel(path
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void testCreateSymbolicLinkUnsupportedOp() throws IOException {

        provider.newFileSystem(newRepo
                               EMPTY_ENV);


        assertThatThrownBy(() -> provider.createSymbolicLink(link
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void testCreateLinkUnsupportedOp() throws IOException {

        provider.newFileSystem(newRepo
                               EMPTY_ENV);


        assertThatThrownBy(() -> provider.createLink(link
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void testReadSymbolicLinkUnsupportedOp() throws IOException {

        provider.newFileSystem(newRepo
                               EMPTY_ENV);


        assertThatThrownBy(() -> provider.readSymbolicLink(link))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
