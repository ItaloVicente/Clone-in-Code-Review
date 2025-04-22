package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ReadLinesTest {
	List<String> l = new ArrayList<String>();

	@Before
	public void clearList() {
		l.clear();
	}

	@Test
	public void testReadLines_singleLine() {
		l.add("[0]");
		assertEquals(l
	}

	@Test
	public void testReadLines_LF() {
		l.add("[0]");
		l.add("[1]");
		assertEquals(l
	}

	@Test
	public void testReadLines_CRLF() {
		l.add("[0]");
		l.add("[1]");
		assertEquals(l
	}

	@Test
	public void testReadLines_endLF() {
		l.add("[0]");
		l.add("");
		assertEquals(l
	}

	@Test
	public void testReadLines_endCRLF() {
		l.add("[0]");
		l.add("");
		assertEquals(l
	}

	@Test
	public void testReadLines_mixed() {
		l.add("[0]");
		l.add("[1]");
		l.add("[2]");
		assertEquals(l
	}
}
