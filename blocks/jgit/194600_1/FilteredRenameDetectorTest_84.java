
package org.eclipse.jgit.diff;

import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.junit.Before;

public abstract class AbstractRenameDetectionTestCase
		extends RepositoryTestCase {

	protected static final String PATH_A = "src/A";

	protected static final String PATH_B = "src/B";

	protected static final String PATH_H = "src/H";

	protected static final String PATH_Q = "src/Q";

	protected TestRepository<Repository> testDb;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		testDb = new TestRepository<>(db);
	}

	protected ObjectId blob(String content) throws Exception {
		return testDb.blob(content).copy();
	}

	protected static void assertRename(DiffEntry o
			DiffEntry rename) {
		assertEquals(ChangeType.RENAME

		assertEquals(o.getOldPath()
		assertEquals(n.getNewPath()

		assertEquals(o.getOldMode()
		assertEquals(n.getNewMode()

		assertEquals(o.getOldId()
		assertEquals(n.getNewId()

		assertEquals(score
	}

	protected static void assertCopy(DiffEntry o
			DiffEntry copy) {
		assertEquals(ChangeType.COPY

		assertEquals(o.getOldPath()
		assertEquals(n.getNewPath()

		assertEquals(o.getOldMode()
		assertEquals(n.getNewMode()

		assertEquals(o.getOldId()
		assertEquals(n.getNewId()

		assertEquals(score
	}

	protected static void assertAdd(String newName
			FileMode newMode
		assertEquals(DiffEntry.DEV_NULL
		assertEquals(DiffEntry.A_ZERO
		assertEquals(FileMode.MISSING
		assertEquals(ChangeType.ADD
		assertEquals(newName
		assertEquals(AbbreviatedObjectId.fromObjectId(newId)
		assertEquals(newMode
	}

	protected static void assertDelete(String oldName
			FileMode oldMode
		assertEquals(DiffEntry.DEV_NULL
		assertEquals(DiffEntry.A_ZERO
		assertEquals(FileMode.MISSING
		assertEquals(ChangeType.DELETE
		assertEquals(oldName
		assertEquals(AbbreviatedObjectId.fromObjectId(oldId)
		assertEquals(oldMode
	}
}
