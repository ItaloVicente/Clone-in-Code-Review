package org.eclipse.jgit.ignore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.ignore.internal.Strings;
import org.junit.Test;

public class BasicRuleTest {

	@Test
	public void test() {
		FastIgnoreRule rule1 = new FastIgnoreRule("/hello/[a]/");
		FastIgnoreRule rule2 = new FastIgnoreRule("/hello/[a]/");
		FastIgnoreRule rule3 = new FastIgnoreRule("!/hello/[a]/");
		FastIgnoreRule rule4 = new FastIgnoreRule("/hello/[a]");
		assertTrue(rule1.dirOnly());
		assertTrue(rule3.dirOnly());
		assertFalse(rule4.dirOnly());
		assertFalse(rule1.getNegation());
		assertTrue(rule3.getNegation());
		assertNotEquals(rule1
		assertEquals(rule1
		assertEquals(rule1
		assertNotEquals(rule1
		assertNotEquals(rule1
		assertEquals(rule1.hashCode()
		assertNotEquals(rule1.hashCode()
		assertEquals(rule1.toString()
		assertNotEquals(rule1.toString()
	}

	@Test
	public void testDirectoryPattern() {
		assertTrue(Strings.isDirectoryPattern("/"));
		assertTrue(Strings.isDirectoryPattern("/ "));
		assertTrue(Strings.isDirectoryPattern("/     "));
		assertFalse(Strings.isDirectoryPattern("     "));
		assertFalse(Strings.isDirectoryPattern(""));
	}

	@Test
	public void testStripTrailingChar() {
		assertEquals(""
		assertEquals(""
		assertEquals("a"
		assertEquals("a"
		assertEquals("a/ "
	}

	@Test
	public void testStripTrailingWhitespace() {
		assertEquals(""
		assertEquals(""
		assertEquals("a"
		assertEquals("a"
		assertEquals("a"
		assertEquals("a"
	}
}
