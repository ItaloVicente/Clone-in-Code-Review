
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.RefSpec.WildcardMode;
import org.junit.Test;

public class RefSpecTest {
	@Test
	public void testMasterMaster() {
		final String sn = "refs/heads/master";
		final RefSpec rs = new RefSpec(sn + ":" + sn);
		assertFalse(rs.isForceUpdate());
		assertFalse(rs.isWildcard());
		assertEquals(sn
		assertEquals(sn
		assertEquals(sn + ":" + sn
		assertEquals(rs

		Ref r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
		assertTrue(rs.matchSource(r));
		assertTrue(rs.matchDestination(r));
		assertSame(rs

		r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
		assertFalse(rs.matchSource(r));
		assertFalse(rs.matchDestination(r));
	}

	@Test
	public void testSplitLastColon() {
		final String lhs = ":m:a:i:n:t";
		final String rhs = "refs/heads/maint";
		final RefSpec rs = new RefSpec(lhs + ":" + rhs);
		assertFalse(rs.isForceUpdate());
		assertFalse(rs.isWildcard());
		assertEquals(lhs
		assertEquals(rhs
		assertEquals(lhs + ":" + rhs
		assertEquals(rs
	}

	@Test
	public void testForceMasterMaster() {
		final String sn = "refs/heads/master";
		final RefSpec rs = new RefSpec("+" + sn + ":" + sn);
		assertTrue(rs.isForceUpdate());
		assertFalse(rs.isWildcard());
		assertEquals(sn
		assertEquals(sn
		assertEquals("+" + sn + ":" + sn
		assertEquals(rs

		Ref r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
		assertTrue(rs.matchSource(r));
		assertTrue(rs.matchDestination(r));
		assertSame(rs

		r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
		assertFalse(rs.matchSource(r));
		assertFalse(rs.matchDestination(r));
	}

	@Test
	public void testMaster() {
		final String sn = "refs/heads/master";
		final RefSpec rs = new RefSpec(sn);
		assertFalse(rs.isForceUpdate());
		assertFalse(rs.isWildcard());
		assertEquals(sn
		assertNull(rs.getDestination());
		assertEquals(sn
		assertEquals(rs

		Ref r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
		assertTrue(rs.matchSource(r));
		assertFalse(rs.matchDestination(r));
		assertSame(rs

		r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
		assertFalse(rs.matchSource(r));
		assertFalse(rs.matchDestination(r));
	}

	@Test
	public void testForceMaster() {
		final String sn = "refs/heads/master";
		final RefSpec rs = new RefSpec("+" + sn);
		assertTrue(rs.isForceUpdate());
		assertFalse(rs.isWildcard());
		assertEquals(sn
		assertNull(rs.getDestination());
		assertEquals("+" + sn
		assertEquals(rs

		Ref r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
		assertTrue(rs.matchSource(r));
		assertFalse(rs.matchDestination(r));
		assertSame(rs

		r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
		assertFalse(rs.matchSource(r));
		assertFalse(rs.matchDestination(r));
	}

	@Test
	public void testDeleteMaster() {
		final String sn = "refs/heads/master";
		final RefSpec rs = new RefSpec(":" + sn);
		assertFalse(rs.isForceUpdate());
		assertFalse(rs.isWildcard());
		assertNull(rs.getSource());
		assertEquals(sn
		assertEquals(":" + sn
		assertEquals(rs

		Ref r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
		assertFalse(rs.matchSource(r));
		assertTrue(rs.matchDestination(r));
		assertSame(rs

		r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
		assertFalse(rs.matchSource(r));
		assertFalse(rs.matchDestination(r));
	}

	@Test
	public void testForceRemotesOrigin() {
		final RefSpec rs = new RefSpec("+" + srcn + ":" + dstn);
		assertTrue(rs.isForceUpdate());
		assertTrue(rs.isWildcard());
		assertEquals(srcn
		assertEquals(dstn
		assertEquals("+" + srcn + ":" + dstn
		assertEquals(rs

		Ref r;
		RefSpec expanded;

		r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
		assertTrue(rs.matchSource(r));
		assertFalse(rs.matchDestination(r));
		expanded = rs.expandFromSource(r);
		assertNotSame(rs
		assertTrue(expanded.isForceUpdate());
		assertFalse(expanded.isWildcard());
		assertEquals(r.getName()
		assertEquals("refs/remotes/origin/master"

		r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
		assertFalse(rs.matchSource(r));
		assertTrue(rs.matchDestination(r));

		r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
		assertFalse(rs.matchSource(r));
		assertFalse(rs.matchDestination(r));
	}

	@Test
	public void testCreateEmpty() {
		final RefSpec rs = new RefSpec();
		assertFalse(rs.isForceUpdate());
		assertFalse(rs.isWildcard());
		assertEquals("HEAD"
		assertNull(rs.getDestination());
		assertEquals("HEAD"
	}

	@Test
	public void testSetForceUpdate() {
		final RefSpec a = new RefSpec(s);
		assertFalse(a.isForceUpdate());
		RefSpec b = a.setForceUpdate(true);
		assertNotSame(a
		assertFalse(a.isForceUpdate());
		assertTrue(b.isForceUpdate());
		assertEquals(s
		assertEquals("+" + s
	}

	@Test
	public void testSetSource() {
		final RefSpec a = new RefSpec();
		final RefSpec b = a.setSource("refs/heads/master");
		assertNotSame(a
		assertEquals("HEAD"
		assertEquals("refs/heads/master"
	}

	@Test
	public void testSetDestination() {
		final RefSpec a = new RefSpec();
		final RefSpec b = a.setDestination("refs/heads/master");
		assertNotSame(a
		assertEquals("HEAD"
		assertEquals("HEAD:refs/heads/master"
	}

	@Test
	public void testSetDestination_SourceNull() {
		final RefSpec a = new RefSpec();
		RefSpec b;

		b = a.setDestination("refs/heads/master");
		b = b.setSource(null);
		assertNotSame(a
		assertEquals("HEAD"
		assertEquals(":refs/heads/master"
	}

	@Test
	public void testSetSourceDestination() {
		final RefSpec a = new RefSpec();
		final RefSpec b;
		assertNotSame(a
		assertEquals("HEAD"
	}

	@Test
	public void testExpandFromDestination_NonWildcard() {
		final String src = "refs/heads/master";
		final String dst = "refs/remotes/origin/master";
		final RefSpec a = new RefSpec(src + ":" + dst);
		final RefSpec r = a.expandFromDestination(dst);
		assertSame(a
		assertFalse(r.isWildcard());
		assertEquals(src
		assertEquals(dst
	}

	@Test
	public void testExpandFromDestination_Wildcard() {
		final String src = "refs/heads/master";
		final String dst = "refs/remotes/origin/master";
		final RefSpec r = a.expandFromDestination(dst);
		assertNotSame(a
		assertFalse(r.isWildcard());
		assertEquals(src
		assertEquals(dst
	}

	@Test
	public void isWildcardShouldWorkForWildcardSuffixAndComponent() {
		assertFalse(RefSpec.isWildcard("refs/heads/a"));
	}

	@Test
	public void testWildcardInMiddleOfSource() {
		assertTrue(a.isWildcard());
		assertTrue(a.matchSource("refs/pull/a/head"));
		assertTrue(a.matchSource("refs/pull/foo/head"));
		assertTrue(a.matchSource("refs/pull/foo/bar/head"));
		assertFalse(a.matchSource("refs/pull/foo"));
		assertFalse(a.matchSource("refs/pull/head"));
		assertFalse(a.matchSource("refs/pull/foo/head/more"));
		assertFalse(a.matchSource("refs/pullx/head"));

		RefSpec b = a.expandFromSource("refs/pull/foo/head");
		assertEquals("refs/remotes/origin/pr/foo"
		RefSpec c = a.expandFromDestination("refs/remotes/origin/pr/foo");
		assertEquals("refs/pull/foo/head"
	}

	@Test
	public void testWildcardInMiddleOfDestionation() {
		assertTrue(a.isWildcard());
		assertTrue(a.matchDestination("refs/remotes/origin/a/head"));
		assertTrue(a.matchDestination("refs/remotes/origin/foo/head"));
		assertTrue(a.matchDestination("refs/remotes/origin/foo/bar/head"));
		assertFalse(a.matchDestination("refs/remotes/origin/foo"));
		assertFalse(a.matchDestination("refs/remotes/origin/head"));
		assertFalse(a.matchDestination("refs/remotes/origin/foo/head/more"));
		assertFalse(a.matchDestination("refs/remotes/originx/head"));

		RefSpec b = a.expandFromSource("refs/heads/foo");
		assertEquals("refs/remotes/origin/foo/head"
		RefSpec c = a.expandFromDestination("refs/remotes/origin/foo/head");
		assertEquals("refs/heads/foo"
	}

	@Test
	public void testWildcardAfterText1() {
		assertTrue(a.isWildcard());
		assertTrue(a.matchDestination("refs/remotes/mine/x-blah"));
		assertTrue(a.matchDestination("refs/remotes/mine/foo-blah"));
		assertTrue(a.matchDestination("refs/remotes/mine/foo/x-blah"));
		assertFalse(a.matchDestination("refs/remotes/origin/foo/x-blah"));

		RefSpec b = a.expandFromSource("refs/heads/foo/for-linus");
		assertEquals("refs/remotes/mine/foo-blah"
		RefSpec c = a.expandFromDestination("refs/remotes/mine/foo-blah");
		assertEquals("refs/heads/foo/for-linus"
	}

	@Test
	public void testWildcardAfterText2() {
		assertTrue(a.isWildcard());
		assertTrue(a.matchSource("refs/headsx/for-linus"));
		assertTrue(a.matchSource("refs/headsfoo/for-linus"));
		assertTrue(a.matchSource("refs/headsx/foo/for-linus"));
		assertFalse(a.matchSource("refs/headx/for-linus"));

		RefSpec b = a.expandFromSource("refs/headsx/for-linus");
		assertEquals("refs/remotes/mine/x"
		RefSpec c = a.expandFromDestination("refs/remotes/mine/x");
		assertEquals("refs/headsx/for-linus"

		RefSpec d = a.expandFromSource("refs/headsx/foo/for-linus");
		assertEquals("refs/remotes/mine/x/foo"
		RefSpec e = a.expandFromDestination("refs/remotes/mine/x/foo");
		assertEquals("refs/headsx/foo/for-linus"
	}

	@Test
	public void testWildcardMirror() {
		RefSpec a = new RefSpec("*:*");
		assertTrue(a.isWildcard());
		assertTrue(a.matchSource("a"));
		assertTrue(a.matchSource("foo"));
		assertTrue(a.matchSource("foo/bar"));
		assertTrue(a.matchDestination("a"));
		assertTrue(a.matchDestination("foo"));
		assertTrue(a.matchDestination("foo/bar"));

		RefSpec b = a.expandFromSource("refs/heads/foo");
		assertEquals("refs/heads/foo"
		RefSpec c = a.expandFromDestination("refs/heads/foo");
		assertEquals("refs/heads/foo"
	}

	@Test
	public void testWildcardAtStart() {
		assertTrue(a.isWildcard());
		assertTrue(a.matchSource("a/head"));
		assertTrue(a.matchSource("foo/head"));
		assertTrue(a.matchSource("foo/bar/head"));
		assertFalse(a.matchSource("/head"));
		assertFalse(a.matchSource("a/head/extra"));

		RefSpec b = a.expandFromSource("foo/head");
		assertEquals("refs/heads/foo"
		RefSpec c = a.expandFromDestination("refs/heads/foo");
		assertEquals("foo/head"
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenSourceEndsWithSlash() {
		assertNotNull(new RefSpec("refs/heads/"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenDestinationEndsWithSlash() {
		assertNotNull(new RefSpec("refs/heads/master:refs/heads/"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenSourceOnlyAndWildcard() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenDestinationOnlyAndWildcard() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenOnlySourceWildcard() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenOnlyDestinationWildcard() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenMoreThanOneWildcardInSource() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidWhenMoreThanOneWildcardInDestination() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidSourceDoubleSlashes() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidSlashAtStart() {
		assertNotNull(new RefSpec("/foo:/foo"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidDestinationDoubleSlashes() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidSetSource() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidSetDestination() {
	}

	@Test
	public void sourceOnlywithWildcard() {
				WildcardMode.ALLOW_MISMATCH);
		assertTrue(a.matchSource("refs/heads/master"));
		assertNull(a.getDestination());
	}

	@Test
	public void destinationWithWildcard() {
				WildcardMode.ALLOW_MISMATCH);
		assertTrue(a.matchSource("refs/heads/master"));
		assertTrue(a.matchDestination("refs/heads/master"));
		assertTrue(a.matchDestination("refs/heads/foo"));
	}

	@Test
	public void onlyWildCard() {
		RefSpec a = new RefSpec("*"
		assertTrue(a.matchSource("refs/heads/master"));
		assertNull(a.getDestination());
	}
}
