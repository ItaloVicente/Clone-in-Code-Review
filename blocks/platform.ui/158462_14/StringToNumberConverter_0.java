
package org.eclipse.core.databinding.conversion.text;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Format;

import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.internal.databinding.conversion.AbstractNumberToStringConverter;
import org.eclipse.core.internal.databinding.conversion.StringToNumberParser;

public final class NumberToStringConverter extends AbstractNumberToStringConverter {
	private NumberToStringConverter(Format numberFormat, Class<?> fromType) {
		super(numberFormat, fromType);
	}

	public static NumberToStringConverter fromDouble(boolean primitive) {
		return fromDouble(StringToNumberParser.getDefaultNumberFormat(), primitive);
	}

	public static NumberToStringConverter fromDouble(Format numberFormat, boolean primitive) {
		return new NumberToStringConverter(numberFormat, (primitive) ? Double.TYPE : Double.class);
	}

	public static NumberToStringConverter fromLong(boolean primitive) {
		return fromLong(StringToNumberParser.getDefaultIntegerFormat(), primitive);
	}

	public static NumberToStringConverter fromLong(Format numberFormat, boolean primitive) {
		return new NumberToStringConverter(numberFormat, (primitive) ? Long.TYPE : Long.class);
	}

	public static NumberToStringConverter fromFloat(boolean primitive) {
		return fromFloat(StringToNumberParser.getDefaultNumberFormat(), primitive);
	}

	public static NumberToStringConverter fromFloat(Format numberFormat, boolean primitive) {
		return new NumberToStringConverter(numberFormat, (primitive) ? Float.TYPE : Float.class);
	}

	public static NumberToStringConverter fromInteger(boolean primitive) {
		return fromInteger(StringToNumberParser.getDefaultIntegerFormat(), primitive);
	}

	public static NumberToStringConverter fromInteger(Format numberFormat, boolean primitive) {
		return new NumberToStringConverter(numberFormat, (primitive) ? Integer.TYPE : Integer.class);
	}

	public static NumberToStringConverter fromBigInteger() {
		return fromBigInteger(StringToNumberParser.getDefaultIntegerBigDecimalFormat());
	}

	public static NumberToStringConverter fromBigInteger(Format numberFormat) {
		return new NumberToStringConverter(numberFormat, BigInteger.class);
	}

	public static NumberToStringConverter fromBigDecimal() {
		return fromBigDecimal(StringToNumberParser.getDefaultBigDecimalFormat());
	}

	public static NumberToStringConverter fromBigDecimal(Format numberFormat) {
		return new NumberToStringConverter(numberFormat, BigDecimal.class);
	}

	public static NumberToStringConverter fromShort(boolean primitive) {
		return fromShort(StringToNumberParser.getDefaultIntegerFormat(), primitive);
	}

	public static NumberToStringConverter fromShort(Format numberFormat, boolean primitive) {
		return new NumberToStringConverter(numberFormat, (primitive) ? Short.TYPE : Short.class);
	}

	public static NumberToStringConverter fromByte(boolean primitive) {
		return fromByte(StringToNumberParser.getDefaultIntegerFormat(), primitive);
	}

	public static NumberToStringConverter fromByte(Format numberFormat, boolean primitive) {
		return new NumberToStringConverter(numberFormat, (primitive) ? Byte.TYPE : Byte.class);
	}

}
