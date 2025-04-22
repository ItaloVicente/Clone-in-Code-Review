
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.niofs.fs.FileSystemState;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitFileSystemImplProviderWithFoldersTest extends AbstractTestInfra {

    @Test
    public void testNewFileSystemWithSubfolder() throws IOException {
        final FileSystem fs = provider.newFileSystem(newRepo
                                                     EMPTY_ENV);

        assertThat(fs).isNotNull();

        final DirectoryStream<Path> stream = provider.newDirectoryStream(provider.getPath(newRepo)
                                                                         null);
        assertThat(stream).isEmpty();
    }

    @Test
    public void testCreateFileIntoRepositoryWithFolder() throws IOException

        final Map<String
            put("init"
                Boolean.TRUE);
        }};

        final URI oldUri = URI.create(oldPath);
        final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(oldUri
                                                                          env);

        provider.setAttribute(path
                              FileSystemState.FILE_SYSTEM_STATE_ATTR
                              FileSystemState.BATCH);
        final OutputStream outStream = provider.newOutputStream(path);
        assertThat(outStream).isNotNull();
        outStream.write(("my cool content").getBytes());
        outStream.close();

        assertThat(new File(provider.getGitRepoContainerDir()
                            "test/old" + ".git")).exists();

        int commitsCount = 0;
        for (RevCommit com : ((GitImpl) fs.getGit())._log().all().call()) {
            commitsCount++;
        }
    }

    @Test
    public void testExtractPathWithAuthority() throws IOException {

                               new HashMap<String
                                   put("init"
                                       Boolean.TRUE);
                               }});

        final URI uri = URI.create(path);
        final String extracted = provider.extractPath(uri);
        assertThat(extracted).isEqualTo("/readme.md");
    }

    @Test
    public void testComplexExtractPath() throws IOException {

        final FileSystem fs = provider.newFileSystem(newRepo
                                                     EMPTY_ENV);

        final URI uri = URI.create(path);
        final String extracted = provider.extractPath(uri);
        assertThat(extracted).isEqualTo("/readme.md");
    }

    @Test
    public void testExtractComplexRepoName() throws IOException {
                               new HashMap<String
                                   put("init"
                                       Boolean.TRUE);
                               }});

        final URI uri = URI.create(path);
        final String extracted = provider.extractFSNameWithPath(uri);
        assertThat(extracted).isEqualTo("test/repo/readme.md");
    }

    @Test
    public void testExtractSimpleRepoName() {
        final URI uri = URI.create(path);
        final String extracted = provider.extractFSNameWithPath(uri);
        assertThat(extracted).isEqualTo("test/repo/readme.md");
    }

    @Test
    public void testExtractVerySimpleRepoName() {
        final URI uri = URI.create(path);
        final String extracted = provider.extractFSNameWithPath(uri);
        assertThat(extracted).isEqualTo("test/repo/readme.md");
    }
}
