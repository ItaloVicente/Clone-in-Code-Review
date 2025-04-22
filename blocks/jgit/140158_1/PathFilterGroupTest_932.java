
package org.eclipse.jgit.treewalk.filter;

import static org.eclipse.jgit.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Test;

public class NotTreeFilterTest extends RepositoryTestCase {
	@Test
	public void testWrap() throws Exception {
		try (TreeWalk tw = new TreeWalk(db)) {
			final TreeFilter a = TreeFilter.ALL;
			final TreeFilter n = NotTreeFilter.create(a);
			assertNotNull(n);
			assertTrue(a.include(tw));
			assertFalse(n.include(tw));
		}
	}

	@Test
	public void testNegateIsUnwrap() throws Exception {
		final TreeFilter a = PathFilter.create("a/b");
		final TreeFilter n = NotTreeFilter.create(a);
		assertSame(a
	}

	@Test
	public void testShouldBeRecursive_ALL() throws Exception {
		final TreeFilter a = TreeFilter.ALL;
		final TreeFilter n = NotTreeFilter.create(a);
		assertEquals(a.shouldBeRecursive()
	}

	@Test
	public void testShouldBeRecursive_PathFilter() throws Exception {
		final TreeFilter a = PathFilter.create("a/b");
		assertTrue(a.shouldBeRecursive());
		final TreeFilter n = NotTreeFilter.create(a);
		assertTrue(n.shouldBeRecursive());
	}

	@Test
	public void testCloneIsDeepClone() throws Exception {
		final TreeFilter a = new AlwaysCloneTreeFilter();
		assertNotSame(a
		final TreeFilter n = NotTreeFilter.create(a);
		assertNotSame(n
	}

	@Test
	public void testCloneIsSparseWhenPossible() throws Exception {
		final TreeFilter a = TreeFilter.ALL;
		assertSame(a
		final TreeFilter n = NotTreeFilter.create(a);
		assertSame(n
	}
}
