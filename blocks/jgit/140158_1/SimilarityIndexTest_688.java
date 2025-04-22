
package org.eclipse.jgit.diff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.junit.Before;
import org.junit.Test;

public class RenameDetectorTest extends RepositoryTestCase {
	private static final String PATH_A = "src/A";
	private static final String PATH_B = "src/B";
	private static final String PATH_H = "src/H";
	private static final String PATH_Q = "src/Q";

	private RenameDetector rd;

	private TestRepository<Repository> testDb;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		testDb = new TestRepository<>(db);
		rd = new RenameDetector(db);
	}

	@Test
	public void testExactRename_OneRename() throws Exception {
		ObjectId foo = blob("foo");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		rd.add(a);
		rd.add(b);

		List<DiffEntry> entries = rd.compute();
		assertEquals(1
		assertRename(b
	}

	@Test
	public void testExactRename_DifferentObjects() throws Exception {
		ObjectId foo = blob("foo");
		ObjectId bar = blob("bar");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry h = DiffEntry.add(PATH_H
		DiffEntry q = DiffEntry.delete(PATH_Q

		rd.add(a);
		rd.add(h);
		rd.add(q);

		List<DiffEntry> entries = rd.compute();
		assertEquals(3
		assertSame(a
		assertSame(h
		assertSame(q
	}

	@Test
	public void testExactRename_OneRenameOneModify() throws Exception {
		ObjectId foo = blob("foo");
		ObjectId bar = blob("bar");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		DiffEntry c = DiffEntry.modify(PATH_H);
		c.newId = c.oldId = AbbreviatedObjectId.fromObjectId(bar);

		rd.add(a);
		rd.add(b);
		rd.add(c);

		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertRename(b
		assertSame(c
	}

	@Test
	public void testExactRename_ManyRenames() throws Exception {
		ObjectId foo = blob("foo");
		ObjectId bar = blob("bar");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		DiffEntry c = DiffEntry.add(PATH_H
		DiffEntry d = DiffEntry.delete(PATH_B

		rd.add(a);
		rd.add(b);
		rd.add(c);
		rd.add(d);

		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertRename(b
		assertRename(d
	}

	@Test
	public void testExactRename_MultipleIdenticalDeletes() throws Exception {
		ObjectId foo = blob("foo");

		DiffEntry a = DiffEntry.delete(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_B

		DiffEntry c = DiffEntry.delete(PATH_H
		DiffEntry d = DiffEntry.add(PATH_Q

		rd.add(a);
		rd.add(b);
		rd.add(c);
		rd.add(d);

		List<DiffEntry> entries = rd.compute();
		assertEquals(3
		assertEquals(b
		assertEquals(c
		assertRename(a
	}

	@Test
	public void testExactRename_PathBreaksTie() throws Exception {
		ObjectId foo = blob("foo");

		DiffEntry a = DiffEntry.add("src/com/foo/a.java"
		DiffEntry b = DiffEntry.delete("src/com/foo/b.java"

		DiffEntry c = DiffEntry.add("c.txt"
		DiffEntry d = DiffEntry.delete("d.txt"
		DiffEntry e = DiffEntry.add("the_e_file.txt"

		rd.add(a);
		rd.add(d);
		rd.add(e);
		rd.add(b);
		rd.add(c);

		List<DiffEntry> entries = rd.compute();
		assertEquals(3
		assertRename(d
		assertRename(b
		assertCopy(d
	}

	@Test
	public void testExactRename_OneDeleteManyAdds() throws Exception {
		ObjectId foo = blob("foo");

		DiffEntry a = DiffEntry.add("src/com/foo/a.java"
		DiffEntry b = DiffEntry.add("src/com/foo/b.java"
		DiffEntry c = DiffEntry.add("c.txt"

		DiffEntry d = DiffEntry.delete("d.txt"

		rd.add(a);
		rd.add(b);
		rd.add(c);
		rd.add(d);

		List<DiffEntry> entries = rd.compute();
		assertEquals(3
		assertRename(d
		assertCopy(d
		assertCopy(d
	}

	@Test
	public void testExactRename_UnstagedFile() throws Exception {
		ObjectId aId = blob("foo");
		DiffEntry a = DiffEntry.delete(PATH_A
		DiffEntry b = DiffEntry.add(PATH_B

		rd.addAll(Arrays.asList(a
		List<DiffEntry> entries = rd.compute();

		assertEquals(1
		assertRename(a
	}

	@Test
	public void testInexactRename_OnePair() throws Exception {
		ObjectId aId = blob("foo\nbar\nbaz\nblarg\n");
		ObjectId bId = blob("foo\nbar\nbaz\nblah\n");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		rd.add(a);
		rd.add(b);

		List<DiffEntry> entries = rd.compute();
		assertEquals(1
		assertRename(b
	}

	@Test
	public void testInexactRename_OneRenameTwoUnrelatedFiles() throws Exception {
		ObjectId aId = blob("foo\nbar\nbaz\nblarg\n");
		ObjectId bId = blob("foo\nbar\nbaz\nblah\n");
		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		ObjectId cId = blob("some\nsort\nof\ntext\n");
		ObjectId dId = blob("completely\nunrelated\ntext\n");
		DiffEntry c = DiffEntry.add(PATH_B
		DiffEntry d = DiffEntry.delete(PATH_H

		rd.add(a);
		rd.add(b);
		rd.add(c);
		rd.add(d);

		List<DiffEntry> entries = rd.compute();
		assertEquals(3
		assertRename(b
		assertSame(c
		assertSame(d
	}

	@Test
	public void testInexactRename_LastByteDifferent() throws Exception {
		ObjectId aId = blob("foo\nbar\na");
		ObjectId bId = blob("foo\nbar\nb");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		rd.add(a);
		rd.add(b);

		List<DiffEntry> entries = rd.compute();
		assertEquals(1
		assertRename(b
	}

	@Test
	public void testInexactRename_NewlinesOnly() throws Exception {
		ObjectId aId = blob("\n\n\n");
		ObjectId bId = blob("\n\n\n\n");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		rd.add(a);
		rd.add(b);

		List<DiffEntry> entries = rd.compute();
		assertEquals(1
		assertRename(b
	}

	@Test
	public void testInexactRename_SameContentMultipleTimes() throws Exception {
		ObjectId aId = blob("a\na\na\na\n");
		ObjectId bId = blob("a\na\na\n");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		rd.add(a);
		rd.add(b);

		List<DiffEntry> entries = rd.compute();
		assertEquals(1
		assertRename(b
	}

	@Test
	public void testInexactRenames_OnePair2() throws Exception {
		ObjectId aId = blob("ab\nab\nab\nac\nad\nae\n");
		ObjectId bId = blob("ac\nab\nab\nab\naa\na0\na1\n");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		rd.add(a);
		rd.add(b);
		rd.setRenameScore(50);

		List<DiffEntry> entries = rd.compute();
		assertEquals(1
		assertRename(b
	}

	@Test
	public void testNoRenames_SingleByteFiles() throws Exception {
		ObjectId aId = blob("a");
		ObjectId bId = blob("b");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		rd.add(a);
		rd.add(b);

		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertSame(a
		assertSame(b
	}

	@Test
	public void testNoRenames_EmptyFile1() throws Exception {
		ObjectId aId = blob("");
		DiffEntry a = DiffEntry.add(PATH_A

		rd.add(a);

		List<DiffEntry> entries = rd.compute();
		assertEquals(1
		assertSame(a
	}

	@Test
	public void testNoRenames_EmptyFile2() throws Exception {
		ObjectId aId = blob("");
		ObjectId bId = blob("blah");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		rd.add(a);
		rd.add(b);

		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertSame(a
		assertSame(b
	}

	@Test
	public void testNoRenames_SymlinkAndFile() throws Exception {
		ObjectId aId = blob("src/dest");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q
		b.oldMode = FileMode.SYMLINK;

		rd.add(a);
		rd.add(b);

		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertSame(a
		assertSame(b
	}

	@Test
	public void testNoRenames_GitlinkAndFile() throws Exception {
		ObjectId aId = blob("src/dest");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q
		b.oldMode = FileMode.GITLINK;

		rd.add(a);
		rd.add(b);

		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertSame(a
		assertSame(b
	}

	@Test
	public void testNoRenames_SymlinkAndFileSamePath() throws Exception {
		ObjectId aId = blob("src/dest");

		DiffEntry a = DiffEntry.delete(PATH_A
		DiffEntry b = DiffEntry.add(PATH_A
		a.oldMode = FileMode.SYMLINK;

		rd.add(a);
		rd.add(b);

		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertSame(a
		assertSame(b
	}

	@Test
	public void testNoRenames_UntrackedFile() throws Exception {
		ObjectId aId = blob("foo");
		ObjectId bId = ObjectId
				.fromString("3049eb6eee7e1318f4e78e799bf33f1e54af9cbf");

		DiffEntry a = DiffEntry.delete(PATH_A
		DiffEntry b = DiffEntry.add(PATH_B

		rd.addAll(Arrays.asList(a
		List<DiffEntry> entries = rd.compute();

		assertEquals(2
		assertSame(a
		assertSame(b
	}

	@Test
	public void testBreakModify_BreakAll() throws Exception {
		ObjectId aId = blob("foo");
		ObjectId bId = blob("bar");

		DiffEntry m = DiffEntry.modify(PATH_A);
		m.oldId = AbbreviatedObjectId.fromObjectId(aId);
		m.newId = AbbreviatedObjectId.fromObjectId(bId);

		DiffEntry a = DiffEntry.add(PATH_B

		rd.add(a);
		rd.add(m);

		rd.setBreakScore(101);

		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertAdd(PATH_A
		assertRename(DiffEntry.breakModify(m).get(0)
	}

	@Test
	public void testBreakModify_BreakNone() throws Exception {
		ObjectId aId = blob("foo");
		ObjectId bId = blob("bar");

		DiffEntry m = DiffEntry.modify(PATH_A);
		m.oldId = AbbreviatedObjectId.fromObjectId(aId);
		m.newId = AbbreviatedObjectId.fromObjectId(bId);

		DiffEntry a = DiffEntry.add(PATH_B

		rd.add(a);
		rd.add(m);

		rd.setBreakScore(-1);

		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertSame(m
		assertSame(a
	}

	@Test
	public void testBreakModify_BreakBelowScore() throws Exception {
		ObjectId aId = blob("foo");
		ObjectId bId = blob("bar");

		DiffEntry m = DiffEntry.modify(PATH_A);
		m.oldId = AbbreviatedObjectId.fromObjectId(aId);
		m.newId = AbbreviatedObjectId.fromObjectId(bId);

		DiffEntry a = DiffEntry.add(PATH_B

		rd.add(a);
		rd.add(m);


		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertAdd(PATH_A
		assertRename(DiffEntry.breakModify(m).get(0)
	}

	@Test
	public void testBreakModify_DontBreakAboveScore() throws Exception {
		ObjectId aId = blob("blah\nblah\nfoo");
		ObjectId bId = blob("blah\nblah\nbar");

		DiffEntry m = DiffEntry.modify(PATH_A);
		m.oldId = AbbreviatedObjectId.fromObjectId(aId);
		m.newId = AbbreviatedObjectId.fromObjectId(bId);

		DiffEntry a = DiffEntry.add(PATH_B

		rd.add(a);
		rd.add(m);


		List<DiffEntry> entries = rd.compute();
		assertEquals(2
		assertSame(m
		assertSame(a
	}

	@Test
	public void testBreakModify_RejoinIfUnpaired() throws Exception {
		ObjectId aId = blob("foo");
		ObjectId bId = blob("bar");

		DiffEntry m = DiffEntry.modify(PATH_A);
		m.oldId = AbbreviatedObjectId.fromObjectId(aId);
		m.newId = AbbreviatedObjectId.fromObjectId(bId);

		rd.add(m);


		List<DiffEntry> entries = rd.compute();
		assertEquals(1

		DiffEntry modify = entries.get(0);
		assertEquals(m.oldPath
		assertEquals(m.oldId
		assertEquals(m.oldMode
		assertEquals(m.newPath
		assertEquals(m.newId
		assertEquals(m.newMode
		assertEquals(m.changeType
		assertEquals(0
	}

	@Test
	public void testSetRenameScore_IllegalArgs() throws Exception {
		try {
			rd.setRenameScore(-1);
			fail();
		} catch (IllegalArgumentException e) {
		}

		try {
			rd.setRenameScore(101);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testRenameLimit() throws Exception {
		ObjectId aId = blob("foo\nbar\nbaz\nblarg\n");
		ObjectId bId = blob("foo\nbar\nbaz\nblah\n");
		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_B

		ObjectId cId = blob("a\nb\nc\nd\n");
		ObjectId dId = blob("a\nb\nc\n");
		DiffEntry c = DiffEntry.add(PATH_H
		DiffEntry d = DiffEntry.delete(PATH_Q

		rd.add(a);
		rd.add(b);
		rd.add(c);
		rd.add(d);

		rd.setRenameLimit(1);

		assertTrue(rd.isOverRenameLimit());

		List<DiffEntry> entries = rd.compute();
		assertEquals(4
		assertSame(a
		assertSame(b
		assertSame(c
		assertSame(d
	}

	private ObjectId blob(String content) throws Exception {
		return testDb.blob(content).copy();
	}

	private static void assertRename(DiffEntry o
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

	private static void assertCopy(DiffEntry o
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

	private static void assertAdd(String newName
			FileMode newMode
		assertEquals(DiffEntry.DEV_NULL
		assertEquals(DiffEntry.A_ZERO
		assertEquals(FileMode.MISSING
		assertEquals(ChangeType.ADD
		assertEquals(newName
		assertEquals(AbbreviatedObjectId.fromObjectId(newId)
		assertEquals(newMode
	}
}
