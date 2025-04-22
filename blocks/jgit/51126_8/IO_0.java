
package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jgit.lib.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class IOReadLineTest {
	@Parameter(0)
	public boolean buffered;

	@Parameter(1)
	public int sizeHint;

	@SuppressWarnings("boxing")
	@Parameters(name="buffered={0}
	public static Collection<Object[]> getParameters() {
		Boolean[] bv = {false
		Integer[] sv = {-1
		Collection<Object[]> params = new ArrayList<>(bv.length * sv.length);
		for (boolean b : bv) {
			for (Integer s : sv) {
				params.add(new Object[]{b
			}
		}
		return params;
	}

	@Test
	public void testReadLine() throws Exception {
		Reader r = newReader("foo\nbar\nbaz\n");
		assertEquals("foo\n"
		assertEquals("bar\n"
		assertEquals("baz\n"
		assertEquals(""
	}

	@Test
	public void testReadLineNoTrailingNewline() throws Exception {
		Reader r = newReader("foo\nbar\nbaz");
		assertEquals("foo\n"
		assertEquals("bar\n"
		assertEquals("baz"
		assertEquals(""
	}

	private String readLine(Reader r) throws Exception {
		return IO.readLine(r
	}

	private Reader newReader(String in) {
		Reader r = new InputStreamReader(
				new ByteArrayInputStream(Constants.encode(in)));
		if (buffered) {
			r = new BufferedReader(r);
		}
		assertEquals(Boolean.valueOf(buffered)
				Boolean.valueOf(r.markSupported()));
		return r;
	}
}
