package org.eclipse.jgit.niofs.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.Path;

import org.junit.Test;

public class JGitFileSystemImplProviderGCTest extends BaseTest {

	@Test
	public void testGC() throws IOException {

		final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(newRepo

		assertThat(fs).isNotNull();

		final DirectoryStream<Path> stream = provider.newDirectoryStream(provider.getPath(newRepo)
		assertThat(stream).isNotNull().hasSize(0);

		try {
			provider.newFileSystem(newRepo
			failBecauseExceptionWasNotThrown(FileSystemAlreadyExistsException.class);
		} catch (final Exception ex) {
		}

		for (int i = 0; i < 19; i++) {
			assertThat(fs.getNumberOfCommitsSinceLastGC()).isEqualTo(i);


			final OutputStream outStream = provider.newOutputStream(path);
			assertThat(outStream).isNotNull();
			outStream.write(("my cool" + i + " content").getBytes());
			outStream.close();
		}


		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write("my cool content".getBytes());
		outStream.close();
		assertThat(fs.getNumberOfCommitsSinceLastGC()).isEqualTo(0);

		final OutputStream outStream2 = provider.newOutputStream(path);
		assertThat(outStream2).isNotNull();
		outStream2.write("my co dwf sdf ol content".getBytes());
		outStream2.close();
		assertThat(fs.getNumberOfCommitsSinceLastGC()).isEqualTo(1);
	}
}
