
package org.eclipse.jgit.treewalk;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.Constants.encode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.TreeFormatter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.junit.Test;

public class TreeWalkBasicDiffTest extends RepositoryTestCase {
	@Test
	public void testMissingSubtree_DetectFileAdded_FileModified()
			throws Exception {
		final ObjectId oldTree
		try (ObjectInserter inserter = db.newObjectInserter()) {
			final ObjectId aFileId = inserter.insert(OBJ_BLOB
			bFileId = inserter.insert(OBJ_BLOB
			cFileId1 = inserter.insert(OBJ_BLOB
			cFileId2 = inserter.insert(OBJ_BLOB

			{
				TreeFormatter root = new TreeFormatter();
				{
					TreeFormatter subA = new TreeFormatter();
					subA.append("empty"
					root.append("sub-a"
				}
				{
					TreeFormatter subC = new TreeFormatter();
					subC.append("empty"
					root.append("sub-c"
				}
				oldTree = inserter.insert(root);
			}

			{
				TreeFormatter root = new TreeFormatter();
				{
					TreeFormatter subA = new TreeFormatter();
					subA.append("empty"
					root.append("sub-a"
				}
				{
					TreeFormatter subB = new TreeFormatter();
					subB.append("empty"
					root.append("sub-b"
				}
				{
					TreeFormatter subC = new TreeFormatter();
					subC.append("empty"
					root.append("sub-c"
				}
				newTree = inserter.insert(root);
			}
			inserter.flush();
		}

		try (TreeWalk tw = new TreeWalk(db)) {
			tw.reset(oldTree
			tw.setRecursive(true);
			tw.setFilter(TreeFilter.ANY_DIFF);

			assertTrue(tw.next());
			assertEquals("sub-b/empty"
			assertEquals(FileMode.MISSING
			assertEquals(FileMode.REGULAR_FILE
			assertEquals(ObjectId.zeroId()
			assertEquals(bFileId

			assertTrue(tw.next());
			assertEquals("sub-c/empty"
			assertEquals(FileMode.REGULAR_FILE
			assertEquals(FileMode.REGULAR_FILE
			assertEquals(cFileId1
			assertEquals(cFileId2

			assertFalse(tw.next());
		}
	}
}
