
package org.eclipse.jgit.java7;

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

public class FSTest {

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
		fs.createSymLink(link
		assertTrue(fs.exists(link));
		String target = fs.readSymLink(link);
		assertEquals("y"
		assertTrue(fs.lastModified(link) > 0);
		assertTrue(fs.exists(link));
		assertFalse(fs.canExecute(link));
		assertEquals(1

		RepositoryTestCase.fsTick(link);
		File targetFile = new File(trash
		FileUtils.createNewFile(targetFile);
		assertTrue(fs.exists(link));
		assertTrue(fs.lastModified(link) > 0);
		assertTrue(fs.lastModified(targetFile) > fs.lastModified(link));
		assertFalse(fs.canExecute(link));
		fs.setExecute(targetFile
		assertFalse(fs.canExecute(link));
	}

}
