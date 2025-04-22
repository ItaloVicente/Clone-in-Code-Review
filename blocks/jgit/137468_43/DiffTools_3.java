package org.eclipse.jgit.internal.diffmergetool;

import java.io.File;
import java.nio.file.Files;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FS_POSIX;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;

public abstract class ExternalToolTest extends RepositoryTestCase {

	protected static final String DEFAULT_CONTENT = "line1";

	protected File localFile;

	protected File remoteFile;

	protected File mergedFile;

	protected File baseFile;

	protected File commandResult;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		localFile = writeTrashFile("localFile.txt"
		localFile.deleteOnExit();
		remoteFile = writeTrashFile("remoteFile.txt"
		remoteFile.deleteOnExit();
		mergedFile = writeTrashFile("mergedFile.txt"
		mergedFile.deleteOnExit();
		baseFile = writeTrashFile("baseFile.txt"
		baseFile.deleteOnExit();
		commandResult = writeTrashFile("commandResult.txt"
		commandResult.deleteOnExit();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		Files.delete(localFile.toPath());
		Files.delete(remoteFile.toPath());
		Files.delete(mergedFile.toPath());
		Files.delete(baseFile.toPath());
		Files.delete(commandResult.toPath());

		super.tearDown();
	}


	protected static void assumePosixPlatform() {
		Assume.assumeTrue(
				"This test can run only in Linux tests"
				FS.DETECTED instanceof FS_POSIX);
	}
}
