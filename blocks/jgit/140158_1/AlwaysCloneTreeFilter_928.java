package org.eclipse.jgit.treewalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.util.FS;
import org.junit.Test;

public class TreeWalkJava7Test extends RepositoryTestCase {
	@Test
	public void testSymlinkToDirNotRecursingViaSymlink() throws Exception {
		org.junit.Assume.assumeTrue(FS.DETECTED.supportsSymlinks());
		FS fs = db.getFS();
		assertTrue(fs.supportsSymlinks());
		writeTrashFile("target/data"
		fs.createSymLink(new File(trash
		try (TreeWalk tw = new TreeWalk(db)) {
			tw.setRecursive(true);
			tw.addTree(new FileTreeIterator(db));
			assertTrue(tw.next());
			assertEquals("link"
			assertTrue(tw.next());
			assertEquals("target/data"
			assertFalse(tw.next());
		}
	}
}
