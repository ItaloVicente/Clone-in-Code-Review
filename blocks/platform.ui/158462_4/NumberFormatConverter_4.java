
package org.eclipse.core.internal.databinding.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Format;
import java.text.ParsePosition;

import org.eclipse.core.internal.databinding.BindingMessages;

import java.text.NumberFormat;

public class StringToNumberParser2 {
	private static final BigDecimal FLOAT_MAX_BIG_DECIMAL = BigDecimal.valueOf(Float.MAX_VALUE);
	private static final BigDecimal FLOAT_MIN_BIG_DECIMAL = BigDecimal.valueOf(-Float.MAX_VALUE);

	private static final BigDecimal DOUBLE_MAX_BIG_DECIMAL = BigDecimal.valueOf(Double.MAX_VALUE);
	private static final BigDecimal DOUBLE_MIN_BIG_DECIMAL = BigDecimal.valueOf(-Double.MAX_VALUE);

	public static ParseResult parse(Object value, NumberFormat numberFormat,
			boolean primitive) {
		if (!(value instanceof String)) {
			throw new IllegalArgumentException(
					"Value to convert is not a String"); //$NON-NLS-1$
		}

		String source = (String) value;
		ParseResult result = new ParseResult();
		if (!primitive && source.trim().length() == 0) {
			return result;
		}

		synchronized (numberFormat) {
			ParsePosition position = new ParsePosition(0);
			Number parseResult = null;
			parseResult = numberFormat.parse(source, position);

			if (position.getIndex() != source.length()
					|| position.getErrorIndex() > -1) {

				result.position = position;
			} else {
				result.number = parseResult;
			}
		}

		return result;
	}

	public static class ParseResult {

		public Number getNumber() {
			return number;
		}

		public ParsePosition getPosition() {
			return position;
		}
	}

	public static String createParseErrorMessage(String value,
			ParsePosition position) {
		int errorIndex = (position.getErrorIndex() > -1) ? position
				.getErrorIndex() : position.getIndex();

		if (errorIndex < value.length()) {
			return BindingMessages.formatString(
					BindingMessages.VALIDATE_NUMBER_PARSE_ERROR, new Object[] {
							value, Integer.valueOf(errorIndex + 1),
							Character.valueOf(value.charAt(errorIndex)) });
		}
		return BindingMessages.formatString(
				BindingMessages.VALIDATE_NUMBER_PARSE_ERROR_NO_CHARACTER,
				new Object[] { value, Integer.valueOf(errorIndex + 1) });
	}

	public static String createOutOfRangeMessage(Number minValue,
			Number maxValue, Format numberFormat) {
		String min = null;
		String max = null;

		synchronized (numberFormat) {
			min = numberFormat.format(minValue);
			max = numberFormat.format(maxValue);
		}

		return BindingMessages.formatString(
				"Validate_NumberOutOfRangeError", new Object[] { min, max }); //$NON-NLS-1$
	}

	public static boolean inIntegerRange(Number number) {
		return checkInteger(number, 31);
	}

	private static boolean checkInteger(Number number, int bitLength) {
		BigInteger bigInteger = null;

		if (number instanceof Integer || number instanceof Long) {
			bigInteger = BigInteger.valueOf(number.longValue());
		} else if (number instanceof Float || number instanceof Double) {
			double doubleValue = number.doubleValue();
			if (!Double.isNaN(doubleValue) && !Double.isInfinite(doubleValue)) {
				bigInteger = BigDecimal.valueOf(doubleValue).toBigInteger();
			} else {
				return false;
			}
		} else if (number instanceof BigInteger) {
			bigInteger = (BigInteger) number;
		} else if (number instanceof BigDecimal) {
			bigInteger = ((BigDecimal) number).toBigInteger();
		} else {
			bigInteger = BigDecimal.valueOf(number.doubleValue()).toBigInteger();
		}

		if (bigInteger != null) {
			return bigInteger.bitLength() <= bitLength;
		}

		throw new IllegalArgumentException(
				"Number of type [" + number.getClass().getName() + "] is not supported."); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static boolean inLongRange(Number number) {
		return checkInteger(number, 63);
	}

	public static boolean inFloatRange(Number number) {
		return checkDecimal(number, FLOAT_MIN_BIG_DECIMAL,
				FLOAT_MAX_BIG_DECIMAL);
	}

	private static boolean checkDecimal(Number number, BigDecimal min,
			BigDecimal max) {
		BigDecimal bigDecimal = null;
		if (number instanceof Integer || number instanceof Long) {
			bigDecimal = BigDecimal.valueOf(number.doubleValue());
		} else if (number instanceof Float || number instanceof Double) {
			double doubleValue = number.doubleValue();

			if (!Double.isNaN(doubleValue) && !Double.isInfinite(doubleValue)) {
				bigDecimal = BigDecimal.valueOf(doubleValue);
			} else {
				return false;
			}
		} else if (number instanceof BigInteger) {
			bigDecimal = new BigDecimal((BigInteger) number);
		} else if (number instanceof BigDecimal) {
			bigDecimal = (BigDecimal) number;
		} else {
			double doubleValue = number.doubleValue();

			if (!Double.isNaN(doubleValue) && !Double.isInfinite(doubleValue)) {
				bigDecimal = BigDecimal.valueOf(doubleValue);
			} else {
				return false;
			}
		}

				&& min.compareTo(bigDecimal) <= 0;

	}

	public static boolean inDoubleRange(Number number) {
		return checkDecimal(number, DOUBLE_MIN_BIG_DECIMAL,
				DOUBLE_MAX_BIG_DECIMAL);
	}

	public static boolean inShortRange(Number number) {
		return checkInteger(number, 15);
	}

	public static boolean inByteRange(Number number) {
		return checkInteger(number, 7);
	}
}
