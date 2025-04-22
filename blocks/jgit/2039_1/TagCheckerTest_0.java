
package org.eclipse.jgit.lib.objectcheckers;

import junit.framework.TestCase;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.lib.Constants;

public class CommitCheckerTest extends TestCase {
	private CommitChecker checker= new CommitChecker();


	public void testValidCommitNoParent() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("author A. U. Thor <author@localhost> 1 +0000\n");
		b.append("committer A. U. Thor <author@localhost> 1 +0000\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testValidCommitBlankAuthor() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("author <> 0 +0000\n");
		b.append("committer <> 0 +0000\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testValidCommit1Parent() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("parent ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("author A. U. Thor <author@localhost> 1 +0000\n");
		b.append("committer A. U. Thor <author@localhost> 1 +0000\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testValidCommit2Parent() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("parent ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("parent ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("author A. U. Thor <author@localhost> 1 +0000\n");
		b.append("committer A. U. Thor <author@localhost> 1 +0000\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testValidCommit128Parent() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		for (int i = 0; i < 128; i++) {
			b.append("parent ");
			b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
			b.append('\n');
		}

		b.append("author A. U. Thor <author@localhost> 1 +0000\n");
		b.append("committer A. U. Thor <author@localhost> 1 +0000\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testValidCommitNormalTime() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();
		final String when = "1222757360 -0730";

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("author A. U. Thor <author@localhost> " + when + "\n");
		b.append("committer A. U. Thor <author@localhost> " + when + "\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testInvalidCommitNoTree1() {
		final StringBuilder b = new StringBuilder();

		b.append("parent ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("no tree header"
		}
	}

	public void testInvalidCommitNoTree2() {
		final StringBuilder b = new StringBuilder();

		b.append("trie ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("no tree header"
		}
	}

	public void testInvalidCommitNoTree3() {
		final StringBuilder b = new StringBuilder();

		b.append("tree");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("no tree header"
		}
	}

	public void testInvalidCommitNoTree4() {
		final StringBuilder b = new StringBuilder();

		b.append("tree\t");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("no tree header"
		}
	}

	public void testInvalidCommitInvalidTree1() {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("zzzzfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("invalid tree"
		}
	}

	public void testInvalidCommitInvalidTree2() {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append("z\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("invalid tree"
		}
	}

	public void testInvalidCommitInvalidTree3() {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9b");
		b.append("\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("invalid tree"
		}
	}

	public void testInvalidCommitInvalidTree4() {
		final StringBuilder b = new StringBuilder();

		b.append("tree  ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("invalid tree"
		}
	}

	public void testInvalidCommitInvalidParent1() {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("parent ");
		b.append("\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("invalid parent"
		}
	}

	public void testInvalidCommitInvalidParent2() {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("parent ");
		b.append("zzzzfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append("\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("invalid parent"
		}
	}

	public void testInvalidCommitInvalidParent3() {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("parent  ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append("\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("invalid parent"
		}
	}

	public void testInvalidCommitInvalidParent4() {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("parent  ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append("z\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("invalid parent"
		}
	}

	public void testInvalidCommitInvalidParent5() {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("parent\t");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append("\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("no author"
		}
	}

	public void testInvalidCommitNoAuthor() {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("committer A. U. Thor <author@localhost> 1 +0000\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("no author"
		}
	}

	public void testInvalidCommitNoCommitter1() {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("author A. U. Thor <author@localhost> 1 +0000\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("no committer"
		}
	}

	public void testInvalidCommitNoCommitter2() {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("author A. U. Thor <author@localhost> 1 +0000\n");
		b.append("\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("no committer"
		}
	}

	public void testInvalidCommitInvalidAuthor1() {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("author A. U. Thor <foo 1 +0000\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("invalid author"
		}
	}

	public void testInvalidCommitInvalidAuthor2() {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("author A. U. Thor foo> 1 +0000\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("invalid author"
		}
	}

	public void testInvalidCommitInvalidAuthor3() {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("author 1 +0000\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("invalid author"
		}
	}

	public void testInvalidCommitInvalidAuthor4() {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("author a <b> +0000\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("invalid author"
		}
	}

	public void testInvalidCommitInvalidAuthor5() {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("author a <b>\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("invalid author"
		}
	}

	public void testInvalidCommitInvalidAuthor6() {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("author a <b> z");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("invalid author"
		}
	}

	public void testInvalidCommitInvalidAuthor7() {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("author a <b> 1 z");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("invalid author"
		}
	}

	public void testInvalidCommitInvalidCommitter() {
		final StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("author a <b> 1 +0000\n");
		b.append("committer a <");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("invalid committer"
		}
	}

}
