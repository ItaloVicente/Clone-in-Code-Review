
package org.eclipse.jgit.lib.objectcheckers;

import junit.framework.TestCase;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.lib.Constants;

public class TreeCheckerTest extends TestCase {
	private TreeChecker checker= new TreeChecker();

	public void testValidEmptyTree() throws CorruptObjectException {
		checker.check(new byte[0]);
	}

	public void testValidTree1() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testValidTree2() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testValidTree3() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testValidTree4() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testValidTree5() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testValidTree6() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testValidTreeSorting1() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testValidTreeSorting2() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testValidTreeSorting3() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testValidTreeSorting4() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testValidTreeSorting5() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testValidTreeSorting6() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testValidTreeSorting7() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testValidTreeSorting8() throws CorruptObjectException {
		final StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		checker.check(data);
	}

	public void testInvalidTreeModeStartsWithZero1() {
		final StringBuilder b = new StringBuilder();
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("mode starts with '0'"
		}
	}

	public void testInvalidTreeModeStartsWithZero2() {
		final StringBuilder b = new StringBuilder();
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("mode starts with '0'"
		}
	}

	public void testInvalidTreeModeStartsWithZero3() {
		final StringBuilder b = new StringBuilder();
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("mode starts with '0'"
		}
	}

	public void testInvalidTreeModeNotOctal1() {
		final StringBuilder b = new StringBuilder();
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("invalid mode character"
		}
	}

	public void testInvalidTreeModeNotOctal2() {
		final StringBuilder b = new StringBuilder();
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("invalid mode character"
		}
	}

	public void testInvalidTreeModeNotSupportedMode1() {
		final StringBuilder b = new StringBuilder();
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("invalid mode 1"
		}
	}

	public void testInvalidTreeModeNotSupportedMode2() {
		final StringBuilder b = new StringBuilder();
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("invalid mode " + 0170000
		}
	}

	public void testInvalidTreeModeMissingName() {
		final StringBuilder b = new StringBuilder();
		b.append("100644");
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("truncated in mode"
		}
	}

	public void testInvalidTreeNameContainsSlash() {
		final StringBuilder b = new StringBuilder();
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("name contains '/'"
		}
	}

	public void testInvalidTreeNameIsEmpty() {
		final StringBuilder b = new StringBuilder();
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("zero length name"
		}
	}

	public void testInvalidTreeNameIsDot() {
		final StringBuilder b = new StringBuilder();
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("invalid name '.'"
		}
	}

	public void testInvalidTreeNameIsDotDot() {
		final StringBuilder b = new StringBuilder();
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("invalid name '..'"
		}
	}

	public void testInvalidTreeTruncatedInName() {
		final StringBuilder b = new StringBuilder();
		b.append("100644 b");
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("truncated in name"
		}
	}

	public void testInvalidTreeTruncatedInObjectId() {
		final StringBuilder b = new StringBuilder();
		b.append("100644 b\0\1\2");
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("truncated in object id"
		}
	}

	public void testInvalidTreeBadSorting1() {
		final StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("incorrectly sorted"
		}
	}

	public void testInvalidTreeBadSorting2() {
		final StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("incorrectly sorted"
		}
	}

	public void testInvalidTreeBadSorting3() {
		final StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("incorrectly sorted"
		}
	}

	public void testInvalidTreeDuplicateNames1() {
		final StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("duplicate entry names"
		}
	}

	public void testInvalidTreeDuplicateNames2() {
		final StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("duplicate entry names"
		}
	}

	public void testInvalidTreeDuplicateNames3() {
		final StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("duplicate entry names"
		}
	}

	public void testInvalidTreeDuplicateNames4() {
		final StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		entry(b
		entry(b
		entry(b
		entry(b
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.check(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("duplicate entry names"
		}
	}

	private static void entry(final StringBuilder b
		b.append(modeName);
		b.append('\0');
		for (int i = 0; i < Constants.OBJECT_ID_LENGTH; i++)
			b.append((char) i);
	}
}
