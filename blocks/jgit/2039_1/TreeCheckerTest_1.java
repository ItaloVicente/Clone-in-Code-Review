
package org.eclipse.jgit.lib.objectcheckers;

import junit.framework.TestCase;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.lib.Constants;

public class TagCheckerTest extends TestCase {
	private TagChecker checker= new TagChecker();

	public void testValidTag() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();

		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("type commit\n");
		b.append("tag test-tag\n");
		b.append("tagger A. U. Thor <author@localhost> 1 +0000\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testInvalidTagNoObject1() {
		final StringBuilder b = new StringBuilder();

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted invalid tag");
		} catch (CorruptObjectException e) {
			assertEquals("no object header"
		}
	}

	public void testInvalidTagNoObject2() {
		final StringBuilder b = new StringBuilder();

		b.append("object\t");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted invalid tag");
		} catch (CorruptObjectException e) {
			assertEquals("no object header"
		}
	}

	public void testInvalidTagNoObject3() {
		final StringBuilder b = new StringBuilder();

		b.append("obejct ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted invalid tag");
		} catch (CorruptObjectException e) {
			assertEquals("no object header"
		}
	}

	public void testInvalidTagNoObject4() {
		final StringBuilder b = new StringBuilder();

		b.append("object ");
		b.append("zz9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted invalid tag");
		} catch (CorruptObjectException e) {
			assertEquals("invalid object"
		}
	}

	public void testInvalidTagNoObject5() {
		final StringBuilder b = new StringBuilder();

		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append(" \n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted invalid tag");
		} catch (CorruptObjectException e) {
			assertEquals("invalid object"
		}
	}

	public void testInvalidTagNoObject6() {
		final StringBuilder b = new StringBuilder();

		b.append("object ");
		b.append("be9");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted invalid tag");
		} catch (CorruptObjectException e) {
			assertEquals("invalid object"
		}
	}

	public void testInvalidTagNoType1() {
		final StringBuilder b = new StringBuilder();

		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted invalid tag");
		} catch (CorruptObjectException e) {
			assertEquals("no type header"
		}
	}

	public void testInvalidTagNoType2() {
		final StringBuilder b = new StringBuilder();

		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("type\tcommit\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted invalid tag");
		} catch (CorruptObjectException e) {
			assertEquals("no type header"
		}
	}

	public void testInvalidTagNoType3() {
		final StringBuilder b = new StringBuilder();

		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("tpye commit\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted invalid tag");
		} catch (CorruptObjectException e) {
			assertEquals("no type header"
		}
	}

	public void testInvalidTagNoType4() {
		final StringBuilder b = new StringBuilder();

		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("type commit");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted invalid tag");
		} catch (CorruptObjectException e) {
			assertEquals("no tag header"
		}
	}

	public void testInvalidTagNoTagHeader1() {
		final StringBuilder b = new StringBuilder();

		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("type commit\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted invalid tag");
		} catch (CorruptObjectException e) {
			assertEquals("no tag header"
		}
	}

	public void testInvalidTagNoTagHeader2() {
		final StringBuilder b = new StringBuilder();

		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("type commit\n");
		b.append("tag\tfoo\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted invalid tag");
		} catch (CorruptObjectException e) {
			assertEquals("no tag header"
		}
	}

	public void testInvalidTagNoTagHeader3() {
		final StringBuilder b = new StringBuilder();

		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("type commit\n");
		b.append("tga foo\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted invalid tag");
		} catch (CorruptObjectException e) {
			assertEquals("no tag header"
		}
	}

	public void testValidTagHasNoTaggerHeader() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();

		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("type commit\n");
		b.append("tag foo\n");

		checker.check(Constants.encodeASCII(b.toString()));
	}

	public void testInvalidTagInvalidTaggerHeader1() {
		final StringBuilder b = new StringBuilder();

		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("type commit\n");
		b.append("tag foo\n");
		b.append("tagger \n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted invalid tag");
		} catch (CorruptObjectException e) {
			assertEquals("invalid tagger"
		}
	}

	public void testInvalidTagInvalidTaggerHeader3() {
		final StringBuilder b = new StringBuilder();

		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("type commit\n");
		b.append("tag foo\n");
		b.append("tagger a < 1 +000\n");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted invalid tag");
		} catch (CorruptObjectException e) {
			assertEquals("invalid tagger"
		}
	}

}
