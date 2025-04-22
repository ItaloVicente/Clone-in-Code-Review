
package org.eclipse.ui.tests.keys;

import org.eclipse.ui.internal.util.Util;

import junit.framework.TestCase;

public class KeysCsvTest extends TestCase {

	public void testReplace() throws Exception {
		final String src = "Test the \"replaceAll\"";
		final String dest = "Test the \"\"replaceAll\"\"";
		String val = Util.replaceAll(src, "\"", "\"\"");
		assertEquals(dest, val);
	}

	public void testReplaceFirst() throws Exception {
		final String src = "\"Hello world!";
		final String dest = "\"\"Hello world!";
		String val = Util.replaceAll(src, "\"", "\"\"");
		assertEquals(dest, val);
	}
}
