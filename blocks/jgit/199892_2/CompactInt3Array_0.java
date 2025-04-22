package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Assert;
import org.junit.Test;

public class CompactInt3ArrayTest {

	private static final byte[] DATA = { 0x00
			0x00
			0x00
			0x00
			0x00
			0x00
			(byte) 0xff
			(byte) 0xff
	};

	private static final CompactInt3Array three = new CompactInt3Array(DATA);

	@Test
	public void threeBytes_size() {
		assertEquals(8
	}

	@Test
	public void threeBytes_get() {
		assertEquals(0
		assertEquals(5
		assertEquals(10
		assertEquals(15
		assertEquals(20
		assertEquals(25
		assertEquals(0xff0000
		assertEquals(0xffffff
		assertThrows(IndexOutOfBoundsException.class
	}

	@Test
	public void threeBytes_getLastValue() {
		assertEquals(0xffffff
	}

	@Test
	public void threeBytes_find() {
		assertEquals(0
		assertEquals(1
		assertEquals(2
		assertEquals(3
		assertEquals(4
		assertEquals(5
		assertEquals(6
		assertEquals(7
		assertThrows(IllegalArgumentException.class
				() -> three.find(Integer.MAX_VALUE));
	}

	@Test
	public void empty() {
		Assert.assertTrue(CompactInt3Array.EMPTY.isEmpty());
		assertEquals(0
		assertThrows(IndexOutOfBoundsException.class
				() -> CompactInt3Array.EMPTY.getLastValue());
		assertThrows(IndexOutOfBoundsException.class
				() -> CompactInt3Array.EMPTY.get(0));
	}
}
