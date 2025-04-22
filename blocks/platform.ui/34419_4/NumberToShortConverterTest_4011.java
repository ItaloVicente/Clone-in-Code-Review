
package org.eclipse.core.tests.internal.databinding.conversion;

import junit.framework.TestCase;

import org.eclipse.core.databinding.conversion.IConverter;

public abstract class NumberToNumberTestHarness extends TestCase {

	protected abstract IConverter doGetToPrimitiveValidator(Class fromType);

	protected abstract IConverter doGetToBoxedTypeValidator(Class fromType);

	protected abstract Class doGetToType(boolean primitive);

	protected abstract Number doGetOutOfRangeNumber();

	public void testFromType() throws Exception {
		Class from = Integer.class;
		assertEquals(from, doGetToBoxedTypeValidator(from).getFromType());
	}

	public void testToTypeIsPrimitive() throws Exception {
		Class toType = doGetToType(true);

		if (toType == null) {
			return;
		}
		assertEquals("to type was not of the correct type", toType, doGetToPrimitiveValidator(Integer.class)
				.getToType());
		assertTrue("to type was not primitive", toType.isPrimitive());
	}

	public void testToTypeIsBoxedType() throws Exception {
		Class toType = doGetToType(false);
		assertEquals(toType, doGetToBoxedTypeValidator(Integer.class)
				.getToType());
		assertFalse(toType.isPrimitive());
	}

	public void testValidConversion() throws Exception {
		Integer value = new Integer(1);
		Number result = (Number) doGetToBoxedTypeValidator(Integer.class)
				.convert(value);

		assertNotNull("result was null", result);

		assertEquals(doGetToType(false), result.getClass());
		assertEquals(value, new Integer(result.intValue()));
	}

	public void testOutOfRangeConversion() throws Exception {
		Number outOfRange = doGetOutOfRangeNumber();

		if (outOfRange == null) {
			return;
		}
		try {
			doGetToBoxedTypeValidator(Integer.class).convert(outOfRange);
			fail("exception should have been thrown");
		} catch (IllegalArgumentException e) {
		}
	}

	public void testConvertNullValueForPrimitiveThrowsIllegalArgumentException()
			throws Exception {
		if (doGetToType(true) == null) {

			return;
		}

		try {
			doGetToPrimitiveValidator(Integer.class).convert(null);
			fail("exception should have been thrown");
		} catch (IllegalArgumentException e) {
		}
	}

	public void testConvertNullValueForBoxedTypeReturnsNull() throws Exception {
		assertNull(doGetToBoxedTypeValidator(Integer.class).convert(null));
	}

	public void testNonNumberThrowsIllegalArgumentException() throws Exception {
		try {
			doGetToBoxedTypeValidator(Integer.class).convert("");
			fail("exception should have been thrown");
		} catch (IllegalArgumentException e) {
		}
	}
}
