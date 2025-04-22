package org.eclipse.jgit.lfs.lib;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MutableLongObjectIdTest {

	@Test
	public void testFromRawLong() {
		MutableLongObjectId m = new MutableLongObjectId();
		m.fromRaw(new long[] { 1L
		assertEquals(new LongObjectId(1L
	}

	@Test
	public void testFromString() {
		AnyLongObjectId id = new LongObjectId(1L
		MutableLongObjectId m = new MutableLongObjectId();
		m.fromString(id.name());
		assertEquals(id
	}

	@Test
	public void testFromStringByte() {
		AnyLongObjectId id = new LongObjectId(1L
		MutableLongObjectId m = new MutableLongObjectId();
		byte[] buf = new byte[64];
		id.copyTo(buf
		m.fromString(buf
		assertEquals(id
	}

	@Test
	public void testCopy() {
		MutableLongObjectId m = new MutableLongObjectId();
		m.fromRaw(new long[] { 1L
		assertEquals(m
	}

	@Test
	public void testToObjectId() {
		MutableLongObjectId m = new MutableLongObjectId();
		m.fromRaw(new long[] { 1L
		assertEquals(m
	}
}
