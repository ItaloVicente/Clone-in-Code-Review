
package org.eclipse.jgit.treewalk;

import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.junit.Test;

public class ForPathTest extends RepositoryTestCase {

	private static final FileMode SYMLINK = FileMode.SYMLINK;

	private static final FileMode REGULAR_FILE = FileMode.REGULAR_FILE;

	private static final FileMode EXECUTABLE_FILE = FileMode.EXECUTABLE_FILE;

	@Test
	public void testFindObjects() throws Exception {
		final DirCache tree0 = DirCache.newInCore();
		final DirCacheBuilder b0 = tree0.builder();
		try (ObjectReader or = db.newObjectReader();
				ObjectInserter oi = db.newObjectInserter()) {

			DirCacheEntry aDotB = createEntry("a.b"
			b0.add(aDotB);
			DirCacheEntry aSlashB = createEntry("a/b"
			b0.add(aSlashB);
			DirCacheEntry aSlashCSlashD = createEntry("a/c/d"
			b0.add(aSlashCSlashD);
			DirCacheEntry aZeroB = createEntry("a0b"
			b0.add(aZeroB);
			b0.finish();
			assertEquals(4
			ObjectId tree = tree0.writeTree(oi);

			ObjectId a = null;
			ObjectId aSlashC = null;
			try (TreeWalk tw = new TreeWalk(or)) {
				tw.addTree(tree);
				while (tw.next()) {
					if (tw.getPathString().equals("a")) {
						a = tw.getObjectId(0);
						tw.enterSubtree();
						while (tw.next()) {
							if (tw.getPathString().equals("a/c")) {
								aSlashC = tw.getObjectId(0);
								break;
							}
						}
						break;
					}
				}
			}

			assertEquals(a
			assertEquals(a
			assertEquals(null
			assertEquals(null

			assertEquals(aDotB.getObjectId()
					TreeWalk.forPath(or
			assertEquals(null
			assertEquals(null
			assertEquals(aDotB.getObjectId()
					TreeWalk.forPath(or

			assertEquals(aZeroB.getObjectId()
					TreeWalk.forPath(or

			assertEquals(aSlashB.getObjectId()
					TreeWalk.forPath(or
			assertEquals(aSlashB.getObjectId()
					TreeWalk.forPath(or

			assertEquals(aSlashC
					TreeWalk.forPath(or
			assertEquals(aSlashC

			assertEquals(aSlashCSlashD.getObjectId()
					TreeWalk.forPath(or
			assertEquals(aSlashCSlashD.getObjectId()
					TreeWalk.forPath(or
		}
	}

}
