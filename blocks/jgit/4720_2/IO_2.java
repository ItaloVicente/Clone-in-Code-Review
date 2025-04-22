package org.eclipse.jgit.util;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class ReadLinesTest {
	@Test
	public void testReadLines_singleLine() {
		assertArrayEquals(new String[] { "[0]" }
	}

	@Test
	public void testReadLines_LF() {
		String[] lines = IO.readLines("[0]\n[1]");
		assertArrayEquals(new String[] { "[0]"
	}

	@Test
	public void testReadLines_CRLF() {
		String[] lines = IO.readLines("[0]\r\n[1]");
		assertArrayEquals(new String[] { "[0]"
	}

	@Test
	public void testReadLines_endLF() {
		String[] lines = IO.readLines("[0]\n");
		assertArrayEquals(new String[] { "[0]"
	}

	@Test
	public void testReadLines_endCRLF() {
		String[] lines = IO.readLines("[0]\r\n");
		assertArrayEquals(new String[] { "[0]"
	}

	@Test
	public void testReadLines_mixed() {
		String[] lines = IO.readLines("[0]\r\n[1]\n[2]");
		assertArrayEquals(new String[] { "[0]"
	}
}
