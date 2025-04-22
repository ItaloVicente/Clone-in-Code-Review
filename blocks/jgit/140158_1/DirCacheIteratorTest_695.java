
package org.eclipse.jgit.dircache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.junit.Test;

public class DirCacheFindTest extends RepositoryTestCase {
	@Test
	public void testEntriesWithin() throws Exception {
		final DirCache dc = db.readDirCache();

		final String[] paths = { "a-"
		final DirCacheEntry[] ents = new DirCacheEntry[paths.length];
		for (int i = 0; i < paths.length; i++) {
			ents[i] = new DirCacheEntry(paths[i]);
			ents[i].setFileMode(FileMode.REGULAR_FILE);
		}
		final int aFirst = 1;
		final int aLast = 3;

		final DirCacheBuilder b = dc.builder();
            for (DirCacheEntry ent : ents) {
                b.add(ent);
            }
		b.finish();

		assertEquals(paths.length
		for (int i = 0; i < ents.length; i++)
			assertSame(ents[i]

		{
			final DirCacheEntry[] aContents = dc.getEntriesWithin("a");
			assertNotNull(aContents);
			assertEquals(aLast - aFirst + 1
			for (int i = aFirst
				assertSame(ents[i]
		}
		{
			final DirCacheEntry[] aContents = dc.getEntriesWithin("a/");
			assertNotNull(aContents);
			assertEquals(aLast - aFirst + 1
			for (int i = aFirst
				assertSame(ents[i]
		}
		{
			final DirCacheEntry[] aContents = dc.getEntriesWithin("");
			assertNotNull(aContents);
			assertEquals(ents.length
			for (int i = 0; i < ents.length; i++)
				assertSame(ents[i]
		}

		assertNotNull(dc.getEntriesWithin("a-"));
		assertEquals(0

		assertNotNull(dc.getEntriesWithin("a0b"));
		assertEquals(0

		assertNotNull(dc.getEntriesWithin("zoo"));
		assertEquals(0
	}
}
