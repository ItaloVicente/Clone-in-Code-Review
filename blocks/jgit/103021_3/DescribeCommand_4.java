package org.eclipse.jgit.ignore.internal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringsTest {

	private void testString(String string
		assertEquals(string
		assertEquals(string
	}

	@Test
	public void testCount() {
		testString(""
		testString("/"
		testString("foo"
		testString("/foo"
		testString("foo/"
		testString("/foo/"
		testString("foo/bar"
		testString("/foo/bar/"
		testString(" /foo/ "
	}
}
