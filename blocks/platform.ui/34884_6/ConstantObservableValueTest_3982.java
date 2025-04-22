
package org.eclipse.core.tests.internal.databinding.conversion;

import junit.framework.TestCase;

import org.eclipse.core.internal.databinding.conversion.StringToShortConverter;

import com.ibm.icu.text.NumberFormat;

public class StringToShortConverterTest extends TestCase {
	private NumberFormat numberFormat;
	private StringToShortConverter converter;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		numberFormat = NumberFormat.getIntegerInstance();
		converter = StringToShortConverter.toShort(numberFormat, false);
	}

	public void testConvertsToShort() throws Exception {
		Short value = new Short((short) 1);
		Short result = (Short) converter.convert(numberFormat.format(value));
		
		assertEquals(value, result);
	}

	public void testConvertsToShortPrimitive() throws Exception {
		converter = StringToShortConverter.toShort(numberFormat, true);
		Short value = new Short((short) 1);
		Short result = (Short) converter.convert(numberFormat.format(value));
		assertEquals(value, result);
	}

	public void testFromTypeIsString() throws Exception {
		assertEquals(String.class, converter.getFromType());
	}

	public void testToTypeIsShort() throws Exception {
		assertEquals(Short.class, converter.getToType());
	}
	
	public void testToTypeIsShortPrimitive() throws Exception {
		converter = StringToShortConverter.toShort(true);
		assertEquals(Short.TYPE, converter.getToType());
	}
	
	public void testReturnsNullBoxedTypeForEmptyString() throws Exception {
		assertNull(converter.convert(""));
	}

	public void testThrowsIllegalArgumentExceptionIfAskedToConvertNonString()
			throws Exception {
		try {
			converter.convert(new Integer(1));
			fail("exception should have been thrown");
		} catch (IllegalArgumentException e) {
		}
	}
}
