
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collections;

import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.junit.Test;

public class RevWalkPathFilter1Test extends RevWalkTestCase {
	protected void filter(String path) {
		rw.setTreeFilter(AndTreeFilter.create(PathFilterGroup
				.createFromStrings(Collections.singleton(path))
				TreeFilter.ANY_DIFF));
	}

	@Test
	public void testEmpty_EmptyTree() throws Exception {
		final RevCommit a = commit();
		filter("a");
		markStart(a);
		assertNull(rw.next());
	}

	@Test
	public void testEmpty_NoMatch() throws Exception {
		final RevCommit a = commit(tree(file("0"
		filter("a");
		markStart(a);
		assertNull(rw.next());
	}

	@Test
	public void testSimple1() throws Exception {
		final RevCommit a = commit(tree(file("0"
		filter("0");
		markStart(a);
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testEdits_MatchNone() throws Exception {
		final RevCommit a = commit(tree(file("0"
		final RevCommit b = commit(tree(file("0"
		final RevCommit c = commit(tree(file("0"
		final RevCommit d = commit(tree(file("0"
		filter("a");
		markStart(d);
		assertNull(rw.next());
	}

	@Test
	public void testEdits_MatchAll() throws Exception {
		final RevCommit a = commit(tree(file("0"
		final RevCommit b = commit(tree(file("0"
		final RevCommit c = commit(tree(file("0"
		final RevCommit d = commit(tree(file("0"
		filter("0");
		markStart(d);
		assertCommit(d
		assertCommit(c
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testStringOfPearls_FilePath1() throws Exception {
		final RevCommit a = commit(tree(file("d/f"
		final RevCommit b = commit(tree(file("d/f"
		final RevCommit c = commit(tree(file("d/f"
		filter("d/f");
		markStart(c);

		assertCommit(c
		assertEquals(1
		assertCommit(a

		assertCommit(a
		assertEquals(0
		assertNull(rw.next());
	}

	@Test
	public void testStringOfPearls_FilePath1_NoParentRewriting()
			throws Exception {
		final RevCommit a = commit(tree(file("d/f"
		final RevCommit b = commit(tree(file("d/f"
		final RevCommit c = commit(tree(file("d/f"
		filter("d/f");
		markStart(c);
		rw.setRewriteParents(false);

		assertCommit(c
		assertEquals(1
		assertCommit(b

		assertCommit(a
		assertEquals(0
		assertNull(rw.next());
	}

	@Test
	public void testStringOfPearls_FilePath2() throws Exception {
		final RevCommit a = commit(tree(file("d/f"
		final RevCommit b = commit(tree(file("d/f"
		final RevCommit c = commit(tree(file("d/f"
		final RevCommit d = commit(tree(file("d/f"
		filter("d/f");
		markStart(d);

		assertCommit(c
		assertEquals(1
		assertCommit(a

		assertCommit(a
		assertEquals(0
		assertNull(rw.next());
	}

	@Test
	public void testStringOfPearls_FilePath2_NoParentRewriting()
	throws Exception {
		final RevCommit a = commit(tree(file("d/f"
		final RevCommit b = commit(tree(file("d/f"
		final RevCommit c = commit(tree(file("d/f"
		final RevCommit d = commit(tree(file("d/f"
		filter("d/f");
		markStart(d);
		rw.setRewriteParents(false);

		assertCommit(c
		assertEquals(1
		assertCommit(b

		assertCommit(a
		assertEquals(0
		assertNull(rw.next());
	}

	@Test
	public void testStringOfPearls_DirPath2() throws Exception {
		final RevCommit a = commit(tree(file("d/f"
		final RevCommit b = commit(tree(file("d/f"
		final RevCommit c = commit(tree(file("d/f"
		final RevCommit d = commit(tree(file("d/f"
		filter("d");
		markStart(d);

		assertCommit(c
		assertEquals(1
		assertCommit(a

		assertCommit(a
		assertEquals(0
		assertNull(rw.next());
	}

	@Test
	public void testStringOfPearls_DirPath2_NoParentRewriting()
			throws Exception {
		final RevCommit a = commit(tree(file("d/f"
		final RevCommit b = commit(tree(file("d/f"
		final RevCommit c = commit(tree(file("d/f"
		final RevCommit d = commit(tree(file("d/f"
		filter("d");
		markStart(d);
		rw.setRewriteParents(false);

		assertCommit(c
		assertEquals(1
		assertCommit(b

		assertCommit(a
		assertEquals(0
		assertNull(rw.next());
	}

	@Test
	public void testStringOfPearls_FilePath3() throws Exception {
		final RevCommit a = commit(tree(file("d/f"
		final RevCommit b = commit(tree(file("d/f"
		final RevCommit c = commit(tree(file("d/f"
		final RevCommit d = commit(tree(file("d/f"
		final RevCommit e = commit(tree(file("d/f"
		final RevCommit f = commit(tree(file("d/f"
		final RevCommit g = commit(tree(file("d/f"
		final RevCommit h = commit(tree(file("d/f"
		final RevCommit i = commit(tree(file("d/f"
		filter("d/f");
		markStart(i);

		assertCommit(i
		assertEquals(1
		assertCommit(c

		assertCommit(c
		assertEquals(1
		assertCommit(a

		assertCommit(a
		assertEquals(0
		assertNull(rw.next());
	}

	@Test
	public void testStringOfPearls_FilePath3_NoParentRewriting()
			throws Exception {
		final RevCommit a = commit(tree(file("d/f"
		final RevCommit b = commit(tree(file("d/f"
		final RevCommit c = commit(tree(file("d/f"
		final RevCommit d = commit(tree(file("d/f"
		final RevCommit e = commit(tree(file("d/f"
		final RevCommit f = commit(tree(file("d/f"
		final RevCommit g = commit(tree(file("d/f"
		final RevCommit h = commit(tree(file("d/f"
		final RevCommit i = commit(tree(file("d/f"
		filter("d/f");
		markStart(i);
		rw.setRewriteParents(false);

		assertCommit(i
		assertEquals(1
		assertCommit(h

		assertCommit(c
		assertEquals(1
		assertCommit(b

		assertCommit(a
		assertEquals(0
		assertNull(rw.next());
	}
}
