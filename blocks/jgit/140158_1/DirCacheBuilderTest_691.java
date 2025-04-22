
package org.eclipse.jgit.dircache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.junit.Test;

public class DirCacheBuilderIteratorTest extends RepositoryTestCase {
	@Test
	public void testPathFilterGroup_DoesNotSkipTail() throws Exception {
		final DirCache dc = db.readDirCache();

		final FileMode mode = FileMode.REGULAR_FILE;
		final String[] paths = { "a-"
		final DirCacheEntry[] ents = new DirCacheEntry[paths.length];
		for (int i = 0; i < paths.length; i++) {
			ents[i] = new DirCacheEntry(paths[i]);
			ents[i].setFileMode(mode);
		}
		{
			final DirCacheBuilder b = dc.builder();
                    for (DirCacheEntry ent : ents) {
                        b.add(ent);
                    }
			b.finish();
		}

		final int expIdx = 2;
		final DirCacheBuilder b = dc.builder();
		try (TreeWalk tw = new TreeWalk(db)) {
			tw.addTree(new DirCacheBuildIterator(b));
			tw.setRecursive(true);
			tw.setFilter(PathFilterGroup.createFromStrings(Collections
					.singleton(paths[expIdx])));

			assertTrue("found " + paths[expIdx]
			final DirCacheIterator c = tw.getTree(0
			assertNotNull(c);
			assertEquals(expIdx
			assertSame(ents[expIdx]
			assertEquals(paths[expIdx]
			assertEquals(mode.getBits()
			assertSame(mode
			b.add(c.getDirCacheEntry());

			assertFalse("no more entries"
		}

		b.finish();
		assertEquals(ents.length
		for (int i = 0; i < ents.length; i++)
			assertSame(ents[i]
	}
}
