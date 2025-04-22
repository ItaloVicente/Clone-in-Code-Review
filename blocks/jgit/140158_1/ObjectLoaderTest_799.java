
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.eclipse.jgit.errors.InvalidObjectIdException;
import org.junit.Test;

public class ObjectIdTest {
	@Test
	public void test001_toString() {
		final String x = "def4c620bc3713bb1bb26b808ec9312548e73946";
		final ObjectId oid = ObjectId.fromString(x);
		assertEquals(x
	}

	@Test
	public void test002_toString() {
		final String x = "ff00eedd003713bb1bb26b808ec9312548e73946";
		final ObjectId oid = ObjectId.fromString(x);
		assertEquals(x
	}

	@Test
	public void test003_equals() {
		final String x = "def4c620bc3713bb1bb26b808ec9312548e73946";
		final ObjectId a = ObjectId.fromString(x);
		final ObjectId b = ObjectId.fromString(x);
		assertEquals(a.hashCode()
		assertEquals("a and b are same"
	}

	@Test
	public void test004_isId() {
		assertTrue("valid id"
				.isId("def4c620bc3713bb1bb26b808ec9312548e73946"));
	}

	@Test
	public void test005_notIsId() {
		assertFalse("bob is not an id"
	}

	@Test
	public void test006_notIsId() {
		assertFalse("39 digits is not an id"
				.isId("def4c620bc3713bb1bb26b808ec9312548e7394"));
	}

	@Test
	public void test007_isId() {
		assertTrue("uppercase is accepted"
				.isId("Def4c620bc3713bb1bb26b808ec9312548e73946"));
	}

	@Test
	public void test008_notIsId() {
		assertFalse("g is not a valid hex digit"
				.isId("gef4c620bc3713bb1bb26b808ec9312548e73946"));
	}

	@Test
	public void test009_toString() {
		final String x = "ff00eedd003713bb1bb26b808ec9312548e73946";
		final ObjectId oid = ObjectId.fromString(x);
		assertEquals(x
	}

	@Test
	public void test010_toString() {
		final String x = "0000000000000000000000000000000000000000";
		assertEquals(x
	}

	@Test
	public void test011_toString() {
		final String x = "0123456789ABCDEFabcdef1234567890abcdefAB";
		final ObjectId oid = ObjectId.fromString(x);
		assertEquals(x.toLowerCase(Locale.ROOT)
	}

	@Test(expected = InvalidObjectIdException.class)
	public void testFromString_short() {
		ObjectId.fromString("cafe1234");
	}

	@Test(expected = InvalidObjectIdException.class)
	public void testFromString_nonHex() {
		ObjectId.fromString("0123456789abcdefghij0123456789abcdefghij");
	}

	@Test(expected = InvalidObjectIdException.class)
	public void testFromString_shortNonHex() {
		ObjectId.fromString("6789ghij");
	}

	@Test
	public void testGetByte() {
		byte[] raw = new byte[20];
		for (int i = 0; i < 20; i++)
			raw[i] = (byte) (0xa0 + i);
		ObjectId id = ObjectId.fromRaw(raw);

		assertEquals(raw[0] & 0xff
		assertEquals(raw[0] & 0xff
		assertEquals(raw[1] & 0xff

		for (int i = 2; i < 20; i++)
			assertEquals("index " + i
	}

	@Test
	public void testSetByte() {
		byte[] exp = new byte[20];
		for (int i = 0; i < 20; i++)
			exp[i] = (byte) (0xa0 + i);

		MutableObjectId id = new MutableObjectId();
		id.fromRaw(exp);
		assertEquals(ObjectId.fromRaw(exp).name()

		id.setByte(0
		assertEquals(0x10
		exp[0] = 0x10;
		assertEquals(ObjectId.fromRaw(exp).name()

		for (int p = 1; p < 20; p++) {
			id.setByte(p
			assertEquals(0x10 + p
			exp[p] = (byte) (0x10 + p);
			assertEquals(ObjectId.fromRaw(exp).name()
		}

		for (int p = 0; p < 20; p++) {
			id.setByte(p
			assertEquals(0x80 + p
			exp[p] = (byte) (0x80 + p);
			assertEquals(ObjectId.fromRaw(exp).name()
		}
	}
}
