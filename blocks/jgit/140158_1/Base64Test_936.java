
package org.eclipse.jgit.treewalk.filter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Test;

public class TreeFilterTest extends RepositoryTestCase {
	@Test
	public void testALL_IncludesAnything() throws Exception {
		try (TreeWalk tw = new TreeWalk(db)) {
			tw.addTree(new EmptyTreeIterator());
			assertTrue(TreeFilter.ALL.include(tw));
		}
	}

	@Test
	public void testALL_ShouldNotBeRecursive() throws Exception {
		assertFalse(TreeFilter.ALL.shouldBeRecursive());
	}

	@Test
	public void testALL_IdentityClone() throws Exception {
		assertSame(TreeFilter.ALL
	}

	@Test
	public void testNotALL_IncludesNothing() throws Exception {
		try (TreeWalk tw = new TreeWalk(db)) {
			tw.addTree(new EmptyTreeIterator());
			assertFalse(TreeFilter.ALL.negate().include(tw));
		}
	}

	@Test
	public void testANY_DIFF_IncludesSingleTreeCase() throws Exception {
		try (TreeWalk tw = new TreeWalk(db)) {
			tw.addTree(new EmptyTreeIterator());
			assertTrue(TreeFilter.ANY_DIFF.include(tw));
		}
	}

	@Test
	public void testANY_DIFF_ShouldNotBeRecursive() throws Exception {
		assertFalse(TreeFilter.ANY_DIFF.shouldBeRecursive());
	}

	@Test
	public void testANY_DIFF_IdentityClone() throws Exception {
		assertSame(TreeFilter.ANY_DIFF
	}
}
