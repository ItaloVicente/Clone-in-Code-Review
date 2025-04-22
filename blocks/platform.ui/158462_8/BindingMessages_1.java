
package org.eclipse.core.databinding.conversion.text;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Format;

import org.eclipse.core.internal.databinding.conversion.AbstractStringToNumberConverter;
import org.eclipse.core.internal.databinding.conversion.StringToNumberParser;
import org.eclipse.core.internal.databinding.validation.NumberFormatConverter;

public final class StringToNumberConverter<T extends Number> extends AbstractStringToNumberConverter<T> {
	private StringToNumberConverter(Format numberFormat, Class<T> toType, Number min, Number max,
			Class<T> boxedType) {
		super(numberFormat, toType, min, max, boxedType);
	}

	public static StringToNumberConverter<Integer> toInteger(boolean primitive) {
		return toInteger(StringToNumberParser.getDefaultFormat(), primitive);
	}

	public static StringToNumberConverter<Integer> toInteger(Format numberFormat, boolean primitive) {
		return new StringToNumberConverter<>(numberFormat,
				(primitive) ? Integer.TYPE : Integer.class, MIN_INTEGER,
				MAX_INTEGER, Integer.class);
	}

	public static StringToNumberConverter<Double> toDouble(boolean primitive) {
		return toDouble(StringToNumberParser.getDefaultFormat(), primitive);
	}

	public static StringToNumberConverter<Double> toDouble(Format numberFormat, boolean primitive) {
		return new StringToNumberConverter<>(numberFormat,
				(primitive) ? Double.TYPE : Double.class, MIN_DOUBLE,
				MAX_DOUBLE, Double.class);
	}

	public static StringToNumberConverter<Long> toLong(boolean primitive) {
		return toLong(StringToNumberParser.getDefaultFormat(), primitive);
	}

	public static StringToNumberConverter<Long> toLong(Format numberFormat, boolean primitive) {
		return new StringToNumberConverter<>(numberFormat,
				(primitive) ? Long.TYPE : Long.class, MIN_LONG, MAX_LONG,
				Long.class);
	}

	public static StringToNumberConverter<Float> toFloat(boolean primitive) {
		return toFloat(StringToNumberParser.getDefaultFormat(), primitive);
	}

	public static StringToNumberConverter<Float> toFloat(Format numberFormat, boolean primitive) {
		return new StringToNumberConverter<>(numberFormat,
				(primitive) ? Float.TYPE : Float.class, MIN_FLOAT, MAX_FLOAT,
				Float.class);
	}

	public static StringToNumberConverter<BigInteger> toBigInteger() {
		return toBigInteger(StringToNumberParser.getDefaultFormat());
	}

	public static StringToNumberConverter<BigInteger> toBigInteger(Format numberFormat) {
		return new StringToNumberConverter<>(numberFormat, BigInteger.class,
				null, null, BigInteger.class);
	}

	public static StringToNumberConverter<BigDecimal> toBigDecimal() {
		return toBigDecimal(StringToNumberParser.getDefaultFormat());
	}

	public static StringToNumberConverter<BigDecimal> toBigDecimal(Format numberFormat) {
		return new StringToNumberConverter<>(numberFormat, BigDecimal.class,
				null, null, BigDecimal.class);
	}

	public static StringToNumberConverter<Short> toShort(boolean primitive) {
		return toShort(StringToNumberParser.getDefaultFormat(), primitive);
	}

	public static StringToNumberConverter<Short> toShort(Format numberFormat, boolean primitive) {
		return new StringToNumberConverter<>(numberFormat,
				(primitive) ? Short.TYPE : Short.class, MIN_SHORT,
				MAX_SHORT, Short.class);
	}

	public static StringToNumberConverter<Byte> toByte(boolean primitive) {
		return toByte(StringToNumberParser.getDefaultFormat(), primitive);
	}

	public static StringToNumberConverter<Byte> toByte(Format numberFormat, boolean primitive) {
		return new StringToNumberConverter<>(numberFormat,
				(primitive) ? Byte.TYPE : Byte.class, MIN_BYTE,
				MAX_BYTE, Byte.class);
	}

}
