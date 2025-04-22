
package org.eclipse.jgit.diff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.List;
import org.eclipse.jgit.internal.diff.FilteredRenameDetector;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.junit.Before;
import org.junit.Test;

public class FilteredRenameDetectorTest extends AbstractRenameDetectionTestCase {

	private FilteredRenameDetector frd;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		frd = new FilteredRenameDetector(db);
	}

	@Test
	public void testExactRename() throws Exception {
		ObjectId foo = blob("foo");
		ObjectId bar = blob("bar");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		DiffEntry c = DiffEntry.add(PATH_H
		DiffEntry d = DiffEntry.delete(PATH_B

		List<DiffEntry> changes = Arrays.asList(a
		PathFilter filter = PathFilter.create(PATH_A);
		List<DiffEntry> entries = frd.compute(changes
		assertEquals("Unexpected entries in: " + entries
		assertRename(b
	}

	@Test
	public void testExactRename_multipleFilters() throws Exception {
		ObjectId foo = blob("foo");
		ObjectId bar = blob("bar");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		DiffEntry c = DiffEntry.add(PATH_H
		DiffEntry d = DiffEntry.delete(PATH_B

		List<DiffEntry> changes = Arrays.asList(a
		List<PathFilter> filters = Arrays.asList(PathFilter.create(PATH_A)
				PathFilter.create(PATH_H));
		List<DiffEntry> entries = frd.compute(changes
		assertEquals("Unexpected entries in: " + entries
		assertRename(b
		assertRename(d
	}

	@Test
	public void testInexactRename() throws Exception {
		ObjectId aId = blob("foo\nbar\nbaz\nblarg\n");
		ObjectId bId = blob("foo\nbar\nbaz\nblah\n");
		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		ObjectId cId = blob("some\nsort\nof\ntext\n");
		ObjectId dId = blob("completely\nunrelated\ntext\n");
		DiffEntry c = DiffEntry.add(PATH_B
		DiffEntry d = DiffEntry.delete(PATH_H

		List<DiffEntry> changes = Arrays.asList(a
		PathFilter filter = PathFilter.create(PATH_A);
		List<DiffEntry> entries = frd.compute(changes
		assertEquals("Unexpected entries: " + entries
		assertRename(b
	}

	@Test
	public void testInexactRename_multipleFilters() throws Exception {
		ObjectId aId = blob("foo\nbar\nbaz\nblarg\n");
		ObjectId bId = blob("foo\nbar\nbaz\nblah\n");
		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		ObjectId cId = blob("some\nsort\nof\ntext\n");
		ObjectId dId = blob("completely\nunrelated\ntext\n");
		DiffEntry c = DiffEntry.add(PATH_B
		DiffEntry d = DiffEntry.delete(PATH_H

		List<DiffEntry> changes = Arrays.asList(a
		List<PathFilter> filters = Arrays.asList(PathFilter.create(PATH_A)
				PathFilter.create(PATH_H));
		List<DiffEntry> entries = frd.compute(changes
		assertEquals("Unexpected entries: " + entries
		assertRename(b
		assertSame(d
	}

	@Test
	public void testNoRenames() throws Exception {
		ObjectId aId = blob("");
		ObjectId bId = blob("blah1");
		ObjectId cId = blob("");
		ObjectId dId = blob("blah2");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		DiffEntry c = DiffEntry.add(PATH_H
		DiffEntry d = DiffEntry.delete(PATH_B

		List<DiffEntry> changes = Arrays.asList(a
		PathFilter filter = PathFilter.create(PATH_A);
		List<DiffEntry> entries = frd.compute(changes
		assertEquals("Unexpected entries in: " + entries
		assertSame(a
	}

	@Test
	public void testNoRenames_multipleFilters() throws Exception {
		ObjectId aId = blob("");
		ObjectId bId = blob("blah1");
		ObjectId cId = blob("");
		ObjectId dId = blob("blah2");

		DiffEntry a = DiffEntry.add(PATH_A
		DiffEntry b = DiffEntry.delete(PATH_Q

		DiffEntry c = DiffEntry.add(PATH_H
		DiffEntry d = DiffEntry.delete(PATH_B

		List<DiffEntry> changes = Arrays.asList(a
		List<PathFilter> filters = Arrays.asList(PathFilter.create(PATH_A)
				PathFilter.create(PATH_H));
		List<DiffEntry> entries = frd.compute(changes
		assertEquals("Unexpected entries in: " + entries
		assertSame(a
		assertSame(c
	}
}
