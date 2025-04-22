
package org.eclipse.jgit.lib;

import static org.eclipse.jgit.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.junit.MockSystemReader;
import org.eclipse.jgit.util.SystemReader;
import org.junit.Test;

public class ValidRefNameTest {
	private static void assertValid(boolean exp
		SystemReader instance = SystemReader.getInstance();
		try {
			setUnixSystemReader();
			assertEquals("\"" + name + "\""
					Repository.isValidRefName(name));
			setWindowsSystemReader();
			assertEquals("\"" + name + "\""
					Repository.isValidRefName(name));
		} finally {
			SystemReader.setInstance(instance);
		}
	}

	private static void setWindowsSystemReader() {
		SystemReader.setInstance(new MockSystemReader() {
			{
				setWindows();
			}
		});
	}

	private static void setUnixSystemReader() {
		SystemReader.setInstance(new MockSystemReader() {
			{
				setUnix();
			}
		});
	}

	private static void assertInvalidOnWindows(String name) {
		SystemReader instance = SystemReader.getInstance();
		try {
			setUnixSystemReader();
			assertEquals("\"" + name + "\""
					Repository.isValidRefName(name));
			setWindowsSystemReader();
			assertEquals("\"" + name + "\""
					Repository.isValidRefName(name));
		} finally {
			SystemReader.setInstance(instance);
		}
	}

	private static void assertNormalized(final String name
			final String expected) {
		SystemReader instance = SystemReader.getInstance();
		try {
			setUnixSystemReader();
			String normalized = Repository.normalizeBranchName(name);
			assertEquals("Normalization of " + name
			assertEquals("\"" + normalized + "\""
					Repository.isValidRefName(Constants.R_HEADS + normalized));
			setWindowsSystemReader();
			normalized = Repository.normalizeBranchName(name);
			assertEquals("Normalization of " + name
			assertEquals("\"" + normalized + "\""
					Repository.isValidRefName(Constants.R_HEADS + normalized));
		} finally {
			SystemReader.setInstance(instance);
		}
	}

	@Test
	public void testEmptyString() {
		assertValid(false
		assertValid(false
	}

	@Test
	public void testMustHaveTwoComponents() {
		assertValid(false
		assertValid(true
	}

	@Test
	public void testValidHead() {
		assertValid(true
		assertValid(true
		assertValid(true
		assertValid(true
	}

	@Test
	public void testValidTag() {
		assertValid(true
	}

	@Test
	public void testNoLockSuffix() {
		assertValid(false
	}

	@Test
	public void testNoDirectorySuffix() {
		assertValid(false
	}

	@Test
	public void testNoSpace() {
		assertValid(false
	}

	@Test
	public void testNoAsciiControlCharacters() {
		for (char c = '\0'; c < ' '; c++)
			assertValid(false
	}

	@Test
	public void testNoBareDot() {
		assertValid(false
		assertValid(false
		assertValid(false
		assertValid(false
	}

	@Test
	public void testNoLeadingOrTrailingDot() {
		assertValid(false
		assertValid(false
		assertValid(false
		assertValid(false
	}

	@Test
	public void testContainsDot() {
		assertValid(true
		assertValid(false
	}

	@Test
	public void testNoMagicRefCharacters() {
		assertValid(false
		assertValid(false
		assertValid(false

		assertValid(false
		assertValid(false
		assertValid(false

		assertValid(false
		assertValid(false
		assertValid(false
	}

	@Test
	public void testShellGlob() {
		assertValid(false
		assertValid(false
		assertValid(false

		assertValid(false
		assertValid(false
		assertValid(false

		assertValid(false
		assertValid(false
		assertValid(false
	}

	@Test
	public void testValidSpecialCharacterUnixs() {
		assertValid(true
		assertValid(true
		assertValid(true
		assertValid(true
		assertValid(true
		assertValid(true
		assertValid(true
		assertValid(true
		assertValid(true
		assertValid(true
		assertValid(true
		assertValid(true
		assertValid(true
		assertValid(true
		assertValid(true
		assertValid(true
		assertValid(true
		assertValid(true

		assertValid(false

		assertInvalidOnWindows("refs/heads/\"");
		assertInvalidOnWindows("refs/heads/<");
		assertInvalidOnWindows("refs/heads/>");
		assertInvalidOnWindows("refs/heads/|");
	}

	@Test
	public void testUnicodeNames() {
		assertValid(true
	}

	@Test
	public void testRefLogQueryIsValidRef() {
		assertValid(false
		assertValid(false
	}

	@Test
	public void testWindowsReservedNames() {
		assertInvalidOnWindows("refs/heads/con");
		assertInvalidOnWindows("refs/con/x");
		assertInvalidOnWindows("con/heads/x");
		assertValid(true
		assertValid(true
	}

	@Test
	public void testNormalizeBranchName() {
		assertEquals(""
		assertEquals(""
		assertNormalized("Bug 12345::::Hello World"
		assertNormalized("Bug 12345 :::: Hello World"
		assertNormalized("Bug 12345 :::: Hello::: World"
				"Bug_12345_Hello-World");
		assertNormalized(":::Bug 12345 - Hello World"
		assertNormalized("---Bug 12345 - Hello World"
		assertNormalized("Bug 12345 ---- Hello --- World"
				"Bug_12345_Hello_World");
		assertNormalized("Bug 12345 - Hello World!"
		assertNormalized("Bug 12345 : Hello World!"
		assertNormalized("Bug 12345 _ Hello World!"
		assertNormalized("Bug 12345   -       Hello World!"
				"Bug_12345_Hello_World!");
		assertNormalized(" Bug 12345   -   Hello World! "
				"Bug_12345_Hello_World!");
		assertNormalized(" Bug 12345   -   Hello World!   "
				"Bug_12345_Hello_World!");
		assertNormalized("Bug 12345   -   Hello______ World!"
				"Bug_12345_Hello_World!");
		assertNormalized("_Bug 12345 - Hello World!"
	}

	@Test
	public void testNormalizeWithSlashes() {
		assertNormalized("foo/bar/baz"
		assertNormalized("foo/bar.lock/baz.lock"
		assertNormalized("foo/.git/.git~1/bar"
		assertNormalized(".foo/aux/con/com3.txt/com0/prn/lpt1"
				"foo/+aux/+con/+com3.txt/com0/+prn/+lpt1");
	}

	@Test
	public void testNormalizeWithUnicode() {
				"f\u00f6\u00f6/b\u00e0r/b\u00e9-z");
				"\u5165\u53e3_entrance;/\u51fa\u53e3_ex-it");
	}

	@Test
	public void testNormalizeAlreadyValidRefName() {
		assertNormalized("refs/heads/m.a.s.t.e.r"
		assertNormalized("refs/tags/v1.0-20170223"
	}

	@Test
	public void testNormalizeTrimmedUnicodeAlreadyValidRefName() {
		assertNormalized(" \u00e5ngstr\u00f6m\t"
	}
}
