package org.eclipse.jgit.lib;

import junit.framework.TestCase;

public class ConstantsTest extends TestCase {

	public void testShouldConvertStringsToIntegerTypes() throws Exception {
		assertTypeCode(Constants.OBJ_BLOB
		assertTypeCode(Constants.OBJ_COMMIT
		assertTypeCode(Constants.OBJ_TREE
		assertTypeCode(Constants.OBJ_TAG
	}

	public void testShouldThrowExceptionWhenConvertingInvalidTypeCodeToString() throws Exception {
		try {
			Constants.typeCode("foo");
			fail("Was expecting an exception!");
		} catch (IllegalArgumentException e) {
			assertEquals("Bad object type: foo"
		}
	}

	public void testShouldThrowExceptionWhenConvertingInvalidTypeStringToCode() throws Exception {
		try {
			Constants.typeString(-1);
			fail("Was expecting an exception!");
		} catch (IllegalArgumentException e) {
			assertEquals("Bad object type: -1"
		}
	}

	private void assertTypeCode(int typeCode
		assertEquals(typeCode
		assertEquals(typeString
	}
}
