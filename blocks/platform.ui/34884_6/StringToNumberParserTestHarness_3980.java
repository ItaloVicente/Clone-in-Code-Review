
package org.eclipse.core.tests.internal.databinding.conversion;

import junit.framework.TestCase;

import org.eclipse.core.internal.databinding.conversion.StringToNumberParser;
import org.eclipse.core.internal.databinding.conversion.StringToNumberParser.ParseResult;

import com.ibm.icu.text.NumberFormat;

public class StringToNumberParserTest extends TestCase {
	private NumberFormat integerFormat;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		integerFormat = NumberFormat.getIntegerInstance();
	}

	public void testParseNonStringThrowsIllegalArgumentException()
			throws Exception {
		try {
			StringToNumberParser.parse(new Integer(0), integerFormat, false);
			fail("exception should have been thrown");
		} catch (IllegalArgumentException e) {
		}
	}

	public void testEmptyStringReturnsNullIfNotPrimitive() throws Exception {
		ParseResult result = StringToNumberParser.parse("",
				integerFormat, false);
		assertNull(result.getNumber());
	}

	public void testReturnsParsePositionWhenValueCannotBeParsed()
			throws Exception {
		ParseResult result = StringToNumberParser.parse("adsf",
				integerFormat, false);
		assertNotNull(result.getPosition());
		assertNull(result.getNumber());
	}

	public void testReturnsNumberWhenSuccessfullyParsed() throws Exception {
		Integer number = new Integer(5);
		ParseResult result = StringToNumberParser.parse(integerFormat
				.format(number.longValue()), integerFormat, false);
		assertNull(result.getPosition());
		assertEquals(number.intValue(), result.getNumber().intValue());
	}
}
