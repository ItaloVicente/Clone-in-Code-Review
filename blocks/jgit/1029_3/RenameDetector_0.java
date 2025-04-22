
package org.eclipse.jgit.diff;

import java.util.List;

import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RepositoryTestCase;

public class RenameDetectorTest extends RepositoryTestCase {

	RenameDetector rd;

	TestRepository testDb;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		testDb = new TestRepository(db);
		rd = new RenameDetector(db);
	}

	public void testGetEntriesAddDelete() throws Exception {
		ObjectId foo = testDb.blob("foo").copy();

		DiffEntry a = new DiffEntry();
		a.newId = AbbreviatedObjectId.fromObjectId(foo);
		a.newMode = FileMode.REGULAR_FILE;
		a.newName = "some/file.c";
		a.changeType = ChangeType.ADD;

		DiffEntry b = new DiffEntry();
		b.oldId = AbbreviatedObjectId.fromObjectId(foo);
		b.oldMode = FileMode.REGULAR_FILE;
		b.oldName = "some/other_file.c";
		b.changeType = ChangeType.DELETE;

		rd.addDiffEntry(a);
		rd.addDiffEntry(b);

		List<DiffEntry> entries = rd.getEntries();
		assertEquals(1

		DiffEntry rename = entries.get(0);
		assertNotNull(rename);
		assertTrue(foo.equals(rename.newId.toObjectId()));
		assertTrue(foo.equals(rename.oldId.toObjectId()));
		assertEquals(FileMode.REGULAR_FILE
		assertEquals(FileMode.REGULAR_FILE
		assertEquals(ChangeType.RENAME
		assertEquals("some/file.c"
		assertEquals("some/other_file.c"
	}

	public void testGetEntriesAddDeleteModify() throws Exception {
		ObjectId foo = testDb.blob("foo").copy();
		ObjectId bar = testDb.blob("bar").copy();

		DiffEntry a = new DiffEntry();
		a.newId = AbbreviatedObjectId.fromObjectId(foo);
		a.newMode = FileMode.REGULAR_FILE;
		a.newName = "some/file.c";
		a.changeType = ChangeType.ADD;

		DiffEntry b = new DiffEntry();
		b.oldId = AbbreviatedObjectId.fromObjectId(foo);
		b.oldMode = FileMode.REGULAR_FILE;
		b.oldName = "some/other_file.c";
		b.changeType = ChangeType.DELETE;

		DiffEntry c = new DiffEntry();
		c.newId = c.oldId = AbbreviatedObjectId.fromObjectId(bar);
		c.newMode = c.oldMode = FileMode.REGULAR_FILE;
		c.newName = c.oldName = "some/header.h";
		c.changeType = ChangeType.MODIFY;

		rd.addDiffEntry(a);
		rd.addDiffEntry(b);
		rd.addDiffEntry(c);

		List<DiffEntry> entries = rd.getEntries();
		assertEquals(2

		DiffEntry rename = entries.get(0);
		assertNotNull(rename);
		assertTrue(foo.equals(rename.newId.toObjectId()));
		assertTrue(foo.equals(rename.oldId.toObjectId()));
		assertEquals(FileMode.REGULAR_FILE
		assertEquals(FileMode.REGULAR_FILE
		assertEquals(ChangeType.RENAME
		assertEquals("some/file.c"
		assertEquals("some/other_file.c"

		DiffEntry modify = entries.get(1);
		assertEquals(c
	}

	public void testGetEntriesMultipleRenames() throws Exception {
		ObjectId foo = testDb.blob("foo").copy();
		ObjectId bar = testDb.blob("bar").copy();

		DiffEntry a = new DiffEntry();
		a.newId = AbbreviatedObjectId.fromObjectId(foo);
		a.newMode = FileMode.REGULAR_FILE;
		a.newName = "some/file.c";
		a.changeType = ChangeType.ADD;

		DiffEntry b = new DiffEntry();
		b.oldId = AbbreviatedObjectId.fromObjectId(foo);
		b.oldMode = FileMode.REGULAR_FILE;
		b.oldName = "some/other_file.c";
		b.changeType = ChangeType.DELETE;

		DiffEntry c = new DiffEntry();
		c.newId = AbbreviatedObjectId.fromObjectId(bar);
		c.newMode = FileMode.REGULAR_FILE;
		c.newName = "README";
		c.changeType = ChangeType.ADD;

		DiffEntry d = new DiffEntry();
		d.oldId = AbbreviatedObjectId.fromObjectId(bar);
		d.oldMode = FileMode.REGULAR_FILE;
		d.oldName = "REEDME";
		d.changeType = ChangeType.DELETE;

		rd.addDiffEntry(a);
		rd.addDiffEntry(b);
		rd.addDiffEntry(c);
		rd.addDiffEntry(d);

		List<DiffEntry> entries = rd.getEntries();
		assertEquals(2

		DiffEntry readme = entries.get(0);
		assertNotNull(readme);
		assertTrue(bar.equals(readme.newId.toObjectId()));
		assertTrue(bar.equals(readme.oldId.toObjectId()));
		assertEquals(FileMode.REGULAR_FILE
		assertEquals(FileMode.REGULAR_FILE
		assertEquals(ChangeType.RENAME
		assertEquals("README"
		assertEquals("REEDME"

		DiffEntry somefile = entries.get(1);
		assertNotNull(somefile);
		assertTrue(foo.equals(somefile.newId.toObjectId()));
		assertTrue(foo.equals(somefile.oldId.toObjectId()));
		assertEquals(FileMode.REGULAR_FILE
		assertEquals(FileMode.REGULAR_FILE
		assertEquals(ChangeType.RENAME
		assertEquals("some/file.c"
		assertEquals("some/other_file.c"
	}

}
