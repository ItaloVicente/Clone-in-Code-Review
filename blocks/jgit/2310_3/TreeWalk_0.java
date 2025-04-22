
package org.eclipse.jgit.treewalk;

import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.junit.Test;

public class FindObjectTest extends RepositoryTestCase {

	private static final FileMode SYMLINK = FileMode.SYMLINK;

	private static final FileMode REGULAR_FILE = FileMode.REGULAR_FILE;

	private static final FileMode EXECUTABLE_FILE = FileMode.EXECUTABLE_FILE;

	private DirCacheEntry makeEntry(final String path
			throws Exception {
		final DirCacheEntry ent = new DirCacheEntry(path);
		ent.setFileMode(mode);
		ent.setObjectId(new ObjectInserter.Formatter().idFor(
				Constants.OBJ_BLOB
		return ent;
	}

	@Test
	public void testFindObjects() throws Exception {
		final DirCache tree0 = db.readDirCache();
		final DirCacheBuilder b0 = tree0.builder();
		ObjectReader or = db.newObjectReader();
		ObjectInserter oi = db.newObjectInserter();

		DirCacheEntry aDotB = makeEntry("a.b"
		b0.add(aDotB);
		DirCacheEntry aSlashB = makeEntry("a/b"
		b0.add(aSlashB);
		DirCacheEntry aSlashCSlashD = makeEntry("a/c/d"
		b0.add(aSlashCSlashD);
		DirCacheEntry aZeroB = makeEntry("a0b"
		b0.add(aZeroB);
		b0.finish();
		assertEquals(4
		ObjectId tree = tree0.writeTree(oi);

		TreeWalk tw = new TreeWalk(or);
		tw.addTree(tree);
		ObjectId a = null;
		ObjectId aSlashC = null;
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

		assertEquals(tree
		assertEquals(tree
		assertEquals(tree

		assertEquals(a
		assertEquals(a
		assertEquals(a

		assertEquals(aDotB.getObjectId()
		assertEquals(aDotB.getObjectId()
		assertEquals(aDotB.getObjectId()
				TreeWalk.findObject(tree
		assertEquals(aDotB.getObjectId()

		assertEquals(aZeroB.getObjectId()

		assertEquals(aSlashB.getObjectId()
				TreeWalk.findObject(tree
		assertEquals(aSlashB.getObjectId()

		assertEquals(aSlashC
		assertEquals(aSlashC

		assertEquals(aSlashCSlashD.getObjectId()
				TreeWalk.findObject(tree
		assertEquals(aSlashCSlashD.getObjectId()
				TreeWalk.findObject(a

		or.release();
		oi.release();
	}

}
