
package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FSJava7Test {

	private final File trash = new File(new File("target")

	@Before
	public void setUp() throws Exception {
		FileUtils.delete(trash
		assertTrue(trash.mkdirs());
	}

	@After
	public void tearDown() throws Exception {
		FileUtils.delete(trash
	}

	@Test
	public void testSymlinkAttributes() throws IOException
		FS fs = FS.DETECTED;
		File link = new File(trash
		File target = new File(trash
		fs.createSymLink(link
		assertTrue(fs.exists(link));
		String targetName = fs.readSymLink(link);
		assertEquals("y"
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
		assertTrue(fs.canExecute(target));
	}

}
