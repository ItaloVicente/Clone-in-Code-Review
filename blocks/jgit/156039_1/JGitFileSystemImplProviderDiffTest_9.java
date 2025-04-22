
package org.eclipse.jgit.niofs.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import org.eclipse.jgit.niofs.fs.attribute.VersionAttributes;
import org.eclipse.jgit.niofs.fs.options.CherryPickCopyOption;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

public class JGitFileSystemImplProviderCpMvTest extends AbstractTestInfra {

    @Test
    public void testCopyBranches() throws IOException {
        provider.newFileSystem(newRepo
                               EMPTY_ENV);

        {

            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write("my cool content".getBytes());
            outStream.close();
        }

        {

            final OutputStream outStream2 = provider.newOutputStream(path2);
            outStream2.write("my cool content".getBytes());
            outStream2.close();
        }
        {

            final OutputStream outStream3 = provider.newOutputStream(path3);
            outStream3.write("my cool content".getBytes());
            outStream3.close();
        }


        provider.copy(source
                      target);

                                                                         null);

        assertThat(stream).isNotNull().hasSize(2);

        assertThatThrownBy(() -> provider.copy(source
                .isInstanceOf(FileAlreadyExistsException.class);


        assertThatThrownBy(() -> provider.copy(notExists
                .isInstanceOf(NoSuchFileException.class);
    }

    @Test
    public void testCopyFiles() throws IOException {
        provider.newFileSystem(newRepo
                               EMPTY_ENV);

        {
            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write("my cool content".getBytes());
            outStream.close();
        }
        {
            final OutputStream outStream2 = provider.newOutputStream(path2);
            outStream2.write("my cool content".getBytes());
            outStream2.close();
        }
        {
            final OutputStream outStream3 = provider.newOutputStream(path3);
            outStream3.write("my cool content".getBytes());
            outStream3.close();
        }


        provider.copy(path
                      target);

                                                                         null);

        for (Path path1 : stream) {
            System.out.println("content: " + path1.toUri());
        }

        assertThat(stream).isNotNull().hasSize(3);
    }

    @Test
    public void testCopyDir() throws IOException {
        provider.newFileSystem(newRepo
                               EMPTY_ENV);

        {
            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write("my cool content".getBytes());
            outStream.close();
        }
        {
            final OutputStream outStream2 = provider.newOutputStream(path2);
            outStream2.write("my cool content".getBytes());
            outStream2.close();
        }
        {
            final OutputStream outStream3 = provider.newOutputStream(path3);
            outStream3.write("my cool content".getBytes());
            outStream3.close();
        }

        {

            provider.copy(source
                          target);

            final DirectoryStream<Path> stream = provider.newDirectoryStream(target
                                                                             null);

            assertThat(stream).hasSize(3);
        }

        {

            provider.copy(source
                          target);

            final DirectoryStream<Path> stream = provider.newDirectoryStream(target
                                                                             null);

            assertThat(stream).hasSize(2);
        }

        {

            provider.copy(source
                          target);

            final DirectoryStream<Path> stream = provider.newDirectoryStream(target
                                                                             null);

            assertThat(stream).hasSize(2);
        }

        {

            provider.copy(source
                          target);

            final DirectoryStream<Path> stream = provider.newDirectoryStream(target
                                                                             null);

            assertThat(stream).hasSize(1);
        }

        {

            assertThatThrownBy(() -> provider.copy(source
                    .isInstanceOf(NoSuchFileException.class);
        }

        {

            assertThatThrownBy(() -> provider.copy(source
                    .isInstanceOf(FileAlreadyExistsException.class);
        }
    }

    @Test
    public void testCopyFilesAcrossRepositories() throws IOException {
        provider.newFileSystem(newRepo1
                               EMPTY_ENV);

        provider.newFileSystem(newRepo2
                               EMPTY_ENV);

        {
            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write("my cool content".getBytes());
            outStream.close();
        }


        provider.copy(path
                      target);

                                                                         null);

        for (Path path1 : stream) {
            System.out.println("content: " + path1.toUri());
        }

        assertThat(stream).isNotNull().hasSize(1);
    }

    @Test
    public void testCopyDirAcrossRepositories() throws IOException {
        provider.newFileSystem(newRepo1
                               EMPTY_ENV);

        provider.newFileSystem(newRepo2
                               EMPTY_ENV);

        {
            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write("my cool content".getBytes());
            outStream.close();
        }
        {
            final OutputStream outStream2 = provider.newOutputStream(path2);
            outStream2.write("my cool content".getBytes());
            outStream2.close();
        }
        {
            final OutputStream outStream3 = provider.newOutputStream(path3);
            outStream3.write("my cool content".getBytes());
            outStream3.close();
        }

        {

            provider.copy(source
                          target);

            final DirectoryStream<Path> stream = provider.newDirectoryStream(target
                                                                             null);

            assertThat(stream).isNotNull().hasSize(3);
        }

        {

            provider.copy(source
                          target);

            final DirectoryStream<Path> stream = provider.newDirectoryStream(target
                                                                             null);

            assertThat(stream).isNotNull().hasSize(2);
        }

        {

            provider.copy(source
                          target);

            final DirectoryStream<Path> stream = provider.newDirectoryStream(target
                                                                             null);

            assertThat(stream).isNotNull().hasSize(2);
        }

        {

            assertThatThrownBy(() -> provider.copy(source
                    .isInstanceOf(NoSuchFileException.class);
        }

        {

            assertThatThrownBy(() -> provider.copy(source
                    .isInstanceOf(FileAlreadyExistsException.class);
        }
    }

    @Test
    public void testMoveBranches() throws IOException {
        provider.newFileSystem(newRepo
                               EMPTY_ENV);

        {

            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write("my cool content".getBytes());
            outStream.close();
        }

        {

            final OutputStream outStream2 = provider.newOutputStream(path2);
            outStream2.write("my cool content".getBytes());
            outStream2.close();
        }
        {

            final OutputStream outStream3 = provider.newOutputStream(path3);
            outStream3.write("my cool content".getBytes());
            outStream3.close();
        }


        assertThatThrownBy(() -> provider.move(source
                .isInstanceOf(FileAlreadyExistsException.class);


        try {
            provider.move(source2
                          target2);
        } catch (final Exception e) {
            fail("should not throw");
        }
    }

    @Test
    public void testMoveFiles() throws IOException {
        provider.newFileSystem(newRepo
                               EMPTY_ENV);

        {
            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write("my cool content".getBytes());
            outStream.close();
        }
        {
            final OutputStream outStream2 = provider.newOutputStream(path2);
            outStream2.write("my cool content".getBytes());
            outStream2.close();
        }
        {
            final OutputStream outStream3 = provider.newOutputStream(path3);
            outStream3.write("my cool content".getBytes());
            outStream3.close();
        }


        try {
            provider.move(path
                          target);
        } catch (final Exception e) {
            fail("should move file"
                 e);
        }
    }

    @Test
    public void testMoveDir() throws IOException {
        provider.newFileSystem(newRepo
                               EMPTY_ENV);

        {
            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write("my cool content".getBytes());
            outStream.close();
        }
        {
            final OutputStream outStream2 = provider.newOutputStream(path2);
            outStream2.write("my cool content".getBytes());
            outStream2.close();
        }
        {
            final OutputStream outStream3 = provider.newOutputStream(path3);
            outStream3.write("my cool content".getBytes());
            outStream3.close();
        }

        {

            try {
                provider.move(source
                              target);
            } catch (IOException e) {
                assertThat(e).isInstanceOf(DirectoryNotEmptyException.class);
            }
        }
    }

    @Test
    public void testCherryPick() throws IOException {
        provider.newFileSystem(newRepo
                               EMPTY_ENV);

        {

            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write("my cool content".getBytes());
            outStream.close();
        }

        {

            final OutputStream outStream2 = provider.newOutputStream(path2);
            outStream2.write("my cool content".getBytes());
            outStream2.close();
        }
        {

            final OutputStream outStream3 = provider.newOutputStream(path3);
            outStream3.write("my cool content".getBytes());
            outStream3.close();
        }

        String commit2CherryPick;
        String cherryPickContent = "my 2nd cool content";
        {

            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write(cherryPickContent.getBytes());
            outStream.close();

            final VersionAttributes versionAttributes = provider.readAttributes(path
                                                                                VersionAttributes.class);

            assertThat(versionAttributes.history().records()).isNotNull().hasSize(2);
            commit2CherryPick = versionAttributes.history().records().get(versionAttributes.history().records().size() - 1).id();

            final OutputStream outStream2 = provider.newOutputStream(path);
            outStream2.write("my 3rd cool content".getBytes());
            outStream2.close();
        }


        provider.copy(source
                      target);

        String commit2CherryPick2;
        String cherryPickContent2 = "my 4tn cool content";
        {

            final OutputStream outStream = provider.newOutputStream(path);
            outStream.write(cherryPickContent2.getBytes());
            outStream.close();

            final VersionAttributes versionAttributes = provider.readAttributes(path
                                                                                VersionAttributes.class);

            commit2CherryPick2 = versionAttributes.history().records().get(versionAttributes.history().records().size() - 1).id();
        }

        provider.copy(source
                      target2);

        {
            provider.copy(source
                          target
                          new CherryPickCopyOption(commit2CherryPick));

            assertThat(result).isEqualTo(cherryPickContent);
        }

        {
            provider.copy(source
                          target2
                          new CherryPickCopyOption(commit2CherryPick
                                                   commit2CherryPick2));

            assertThat(result).isEqualTo(cherryPickContent2);
        }
    }

    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
