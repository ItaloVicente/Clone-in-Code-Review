
package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.junit.After;
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
	public void fsTest() throws Exception {
		FS fs = FS.DETECTED;

		if ("FS_Win32" == fs.getClass().getName()) {
			assertFalse(fs.isCaseSensitive());
		} else if (SystemReader.getInstance().isMacOS()) {
			assertFalse(fs.isCaseSensitive());
		} else {
			assertTrue(fs.isCaseSensitive());
		}

		if ("FS_Win32" == fs.getClass().getName()) {
			assertFalse(fs.supportsSymlinks());
		} else {
			assertTrue(fs.supportsSymlinks());
		}

		File file = new File(trash
		assertFalse(fs.exists(file));
		FileUtils.createNewFile(file);

		assertTrue(fs.isFile(file));
		assertFalse(fs.isDirectory(file));
		assertFalse(fs.isSymLink(file));
		assertTrue(fs.canExecute(file));
		assertTrue(fs.exists(file));
		assertEquals(2

		File directory = new File(trash
		assertFalse(fs.exists(directory));
		FileUtils.mkdir(directory);

		assertFalse(fs.isFile(directory));
		assertTrue(fs.isDirectory(directory));
		assertFalse(fs.isSymLink(directory));
		assertTrue(fs.canExecute(directory));
		assertTrue(fs.exists(directory));
		assertEquals(2

		File symlink = new File(trash
		File target = new File(trash
		assertFalse(fs.exists(symlink));
		assertFalse(fs.exists(target));
		fs.createSymLink(symlink

		assertFalse(fs.isFile(symlink));
		assertFalse(fs.isDirectory(symlink));
		assertTrue(fs.isSymLink(symlink));
		assertFalse(fs.canExecute(symlink));
		assertTrue(fs.exists(symlink));
		assertEquals(2

		String targetName = fs.readSymLink(symlink);
		assertEquals(target.getName()



	}

	@Test
	public void testSymlinkAttributes() throws Exception {
		FS fs = FS.DETECTED;
		File link = new File(trash
		File target = new File(trash
		fs.createSymLink(link
		assertTrue(fs.exists(link));
		String targetName = fs.readSymLink(link);
		assertEquals("å"
		assertTrue(fs.lastModified(link) > 0);
		assertTrue(fs.exists(link));
		assertFalse(fs.canExecute(link));
		assertEquals(2
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
		assertTrue(fs.canExecute(target));
	}

}
