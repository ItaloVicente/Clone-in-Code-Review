
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collections;

import org.eclipse.jgit.revwalk.filter.OrRevFilter;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.revwalk.filter.SkipRevFilter;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.junit.Test;

public class TreeRevFilterTest extends RevWalkTestCase {
	private RevFilter treeRevFilter(String path) {
		return new TreeRevFilter(rw
	}

	private static TreeFilter treeFilter(String path) {
		return AndTreeFilter.create(
				PathFilterGroup.createFromStrings(Collections.singleton(path))
				TreeFilter.ANY_DIFF);
	}

	@Test
	public void testStringOfPearls_FilePath1()
			throws Exception {
		RevCommit a = commit(tree(file("d/f"
		RevCommit b = commit(tree(file("d/f"
		RevCommit c = commit(tree(file("d/f"
		rw.setRevFilter(treeRevFilter("d/f"));
		markStart(c);

		assertCommit(c
		assertEquals(1
		assertCommit(b

		assertCommit(a
		assertEquals(0
		assertNull(rw.next());
	}

	@Test
	public void testStringOfPearls_FilePath2() throws Exception {
		RevCommit a = commit(tree(file("d/f"
		RevCommit b = commit(tree(file("d/f"
		RevCommit c = commit(tree(file("d/f"
		RevCommit d = commit(tree(file("d/f"
		rw.setRevFilter(treeRevFilter("d/f"));
		markStart(d);

		assertCommit(c
		assertEquals(1
		assertCommit(b

		assertCommit(a
		assertEquals(0
		assertNull(rw.next());
	}

	@Test
	public void testStringOfPearls_DirPath2() throws Exception {
		RevCommit a = commit(tree(file("d/f"
		RevCommit b = commit(tree(file("d/f"
		RevCommit c = commit(tree(file("d/f"
		RevCommit d = commit(tree(file("d/f"
		rw.setRevFilter(treeRevFilter("d"));
		markStart(d);

		assertCommit(c
		assertEquals(1
		assertCommit(b

		assertCommit(a
		assertEquals(0
		assertNull(rw.next());
	}

	@Test
	public void testStringOfPearls_FilePath3() throws Exception {
		RevCommit a = commit(tree(file("d/f"
		RevCommit b = commit(tree(file("d/f"
		RevCommit c = commit(tree(file("d/f"
		RevCommit d = commit(tree(file("d/f"
		RevCommit e = commit(tree(file("d/f"
		RevCommit f = commit(tree(file("d/f"
		RevCommit g = commit(tree(file("d/f"
		RevCommit h = commit(tree(file("d/f"
		RevCommit i = commit(tree(file("d/f"
		rw.setRevFilter(treeRevFilter("d/f"));
		markStart(i);

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

	@Test
	public void testPathFilterOrOtherFilter() throws Exception {
		RevFilter pathFilter = treeRevFilter("d/f");
		RevFilter skipFilter = SkipRevFilter.create(1);
		RevFilter orFilter = OrRevFilter.create(skipFilter

		RevCommit a = parseBody(commit(5
		RevCommit b = parseBody(commit(5
		RevCommit c = parseBody(commit(5

		rw.setRevFilter(pathFilter);
		markStart(c);
		assertCommit(c
		assertCommit(a

		rw.reset();
		rw.setRevFilter(skipFilter);
		markStart(c);
		assertCommit(b
		assertCommit(a

		rw.reset();
		rw.setRevFilter(orFilter);
		markStart(c);
		assertCommit(c
		assertCommit(b
		assertCommit(a
	}
}
