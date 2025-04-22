package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Assert;
import org.junit.Test;

public class UInt24ArrayTest {

	private static final byte[] DATA = { 0x00
			0x00
			0x00
			0x00
			0x00
			0x00
			(byte) 0xff
			(byte) 0xff
	};

	private static final UInt24Array asArray = new UInt24Array(DATA);

	@Test
	public void uInt24Array_size() {
		assertEquals(8
	}

	@Test
	public void uInt24Array_get() {
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
	public void uInt24Array_getLastValue() {
		assertEquals(0xffffff
	}

	@Test
	public void uInt24Array_find() {
		assertEquals(0
		assertEquals(1
		assertEquals(2
		assertEquals(3
		assertEquals(4
		assertEquals(5
		assertEquals(6
		assertEquals(7
		assertThrows(IllegalArgumentException.class
				() -> asArray.binarySearch(Integer.MAX_VALUE));
	}

	@Test
	public void uInt24Array_empty() {
		Assert.assertTrue(UInt24Array.EMPTY.isEmpty());
		assertEquals(0
		assertEquals(-1
		assertThrows(IndexOutOfBoundsException.class
				() -> UInt24Array.EMPTY.getLastValue());
		assertThrows(IndexOutOfBoundsException.class
				() -> UInt24Array.EMPTY.get(0));
	}
}
