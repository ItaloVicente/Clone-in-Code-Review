
package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;
import static org.junit.Assume.assumeNoException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

import org.eclipse.jgit.errors.CommandFailedException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

public class FSTest {
	private File trash;

	@Before
	public void setUp() throws Exception {
		trash = File.createTempFile("tmp_"
		trash.delete();
		assertTrue("mkdir " + trash
	}

	@After
	public void tearDown() throws Exception {
		FileUtils.delete(trash
	}

	@Test
	public void testSymlinkAttributes() throws IOException
		Assume.assumeTrue(FS.DETECTED.supportsSymlinks());
		FS fs = FS.DETECTED;
		File link = new File(trash
		File target = new File(trash
		fs.createSymLink(link
		assertTrue(fs.exists(link));
		String targetName = fs.readSymLink(link);
		assertEquals("b"
		assertTrue(fs.lastModified(link) > 0);
		assertTrue(fs.exists(link));
		assertFalse(fs.canExecute(link));
		assertEquals(1
		assertFalse(fs.exists(target));
		assertFalse(fs.isFile(target));
		assertFalse(fs.isDirectory(target));
		assertFalse(fs.canExecute(target));

		RepositoryTestCase.fsTick(link);
		FileUtils.createNewFile(target);
		assertTrue(fs.exists(link));
		assertTrue(fs.lastModified(link) > 0);
		assertTrue(fs.lastModified(target) > fs.lastModified(link));
		assertFalse(fs.canExecute(link));
		fs.setExecute(target
		assertFalse(fs.canExecute(link));
		assumeTrue(fs.supportsExecute());
		assertTrue(fs.canExecute(target));
	}

	@Test
	public void testUnicodeFilePath() throws IOException {
		Assume.assumeTrue(FS.DETECTED.supportsSymlinks());
		FS fs = FS.DETECTED;
		File link = new File(trash
		File target = new File(trash

		try {
			link.toPath();
			target.toPath();
		} catch (InvalidPathException e) {
			assumeNoException(e);
		}

		fs.createSymLink(link
		assertTrue(fs.exists(link));
		assertEquals("Ã¥"
	}

	@Test
	public void testExecutableAttributes() throws Exception {
		FS fs = FS.DETECTED.newInstance();
		assumeTrue(fs instanceof FS_POSIX);
		((FS_POSIX) fs).setUmask(0022);

		File f = new File(trash
		assertTrue(f.createNewFile());
		assertFalse(fs.canExecute(f));

		Set<PosixFilePermission> permissions = readPermissions(f);
		assertTrue(!permissions.contains(PosixFilePermission.OTHERS_EXECUTE));
		assertTrue(!permissions.contains(PosixFilePermission.GROUP_EXECUTE));
		assertTrue(!permissions.contains(PosixFilePermission.OWNER_EXECUTE));

		fs.setExecute(f

		permissions = readPermissions(f);
		assertTrue("'owner' execute permission not set"
				permissions.contains(PosixFilePermission.OWNER_EXECUTE));
		assertTrue("'group' execute permission not set"
				permissions.contains(PosixFilePermission.GROUP_EXECUTE));
		assertTrue("'others' execute permission not set"
				permissions.contains(PosixFilePermission.OTHERS_EXECUTE));

		((FS_POSIX) fs).setUmask(0033);
		fs.setExecute(f
		assertFalse(fs.canExecute(f));
		fs.setExecute(f

		permissions = readPermissions(f);
		assertTrue("'owner' execute permission not set"
				permissions.contains(PosixFilePermission.OWNER_EXECUTE));
		assertFalse("'group' execute permission set"
				permissions.contains(PosixFilePermission.GROUP_EXECUTE));
		assertFalse("'others' execute permission set"
				permissions.contains(PosixFilePermission.OTHERS_EXECUTE));
	}

	private Set<PosixFilePermission> readPermissions(File f) throws IOException {
		return Files
				.getFileAttributeView(f.toPath()
				.readAttributes().permissions();
	}

	@Test(expected = CommandFailedException.class)
	public void testReadPipePosixCommandFailure()
			throws CommandFailedException {
		FS fs = FS.DETECTED.newInstance();
		assumeTrue(fs instanceof FS_POSIX);

		FS.readPipe(fs.userHome()
				new String[] { "/bin/sh"
				Charset.defaultCharset().name());
	}

	@Test(expected = CommandFailedException.class)
	public void testReadPipeCommandStartFailure()
			throws CommandFailedException {
		FS fs = FS.DETECTED.newInstance();

		FS.readPipe(fs.userHome()
				  new String[] { "this-command-does-not-exist" }
				  Charset.defaultCharset().name());
	}
}
