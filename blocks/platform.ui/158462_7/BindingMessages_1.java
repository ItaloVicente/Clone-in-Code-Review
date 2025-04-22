
package org.eclipse.core.databinding.conversion.text;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Objects;

import org.eclipse.core.internal.databinding.conversion.StringToNumberParserText;
import org.eclipse.core.internal.databinding.conversion.StringToNumberParserText.ParseResult;
import org.eclipse.core.internal.databinding.validation.NumberFormatConverterText;

public class StringToNumberConverter<T extends Number> extends NumberFormatConverterText<Object, T> {
	private Class<?> toType;
	private NumberFormat numberFormat;

	private final Number min;
	private final Number max;

	private final Class<?> boxedType;

	private static final Integer MIN_INTEGER = Integer.valueOf(Integer.MIN_VALUE);
	private static final Integer MAX_INTEGER = Integer.valueOf(Integer.MAX_VALUE);

	private static final Double MIN_DOUBLE = Double.valueOf(-Double.MAX_VALUE);
	private static final Double MAX_DOUBLE = Double.valueOf(Double.MAX_VALUE);

	private static final Long MIN_LONG = Long.valueOf(Long.MIN_VALUE);
	private static final Long MAX_LONG = Long.valueOf(Long.MAX_VALUE);

	private static final Float MIN_FLOAT = Float.valueOf(-Float.MAX_VALUE);
	private static final Float MAX_FLOAT = Float.valueOf(Float.MAX_VALUE);

	private static final Short MIN_SHORT = Short.valueOf(Short.MIN_VALUE);
	private static final Short MAX_SHORT = Short.valueOf(Short.MAX_VALUE);

	private static final Byte MIN_BYTE = Byte.valueOf(Byte.MIN_VALUE);
	private static final Byte MAX_BYTE = Byte.valueOf(Byte.MAX_VALUE);

	static Class<?> icuBigDecimal = null;
	static Method icuBigDecimalScale = null;
	static Method icuBigDecimalUnscaledValue = null;

	{
		try {
			icuBigDecimal = Class.forName("com.ibm.icu.math.BigDecimal"); //$NON-NLS-1$
			icuBigDecimalScale = icuBigDecimal.getMethod("scale"); //$NON-NLS-1$
			icuBigDecimalUnscaledValue = icuBigDecimal.getMethod("unscaledValue"); //$NON-NLS-1$
					(icuBigDecimal != null)+", icuBigDecimalScale="+(icuBigDecimalScale != null)+ //$NON-NLS-1$
					", icuBigDecimalUnscaledValue="+(icuBigDecimalUnscaledValue != null)); //$NON-NLS-1$ */
		}
		catch(ClassNotFoundException | NoSuchMethodException e) {}
	}

	private StringToNumberConverter(NumberFormat numberFormat, Class<T> toType, Number min, Number max,
			Class<T> boxedType) {
		super(String.class, toType, numberFormat);

		this.toType = Objects.requireNonNull(toType);
		this.numberFormat = Objects.requireNonNull(numberFormat);
		this.min = min;
		this.max = max;
		this.boxedType = Objects.requireNonNull(boxedType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T convert(Object fromObject) {
		ParseResult result = StringToNumberParserText.parse(fromObject,
				numberFormat, toType.isPrimitive());

		if (result.getPosition() != null) {
			throw new IllegalArgumentException(
					StringToNumberParserText
					.createParseErrorMessage((String) fromObject, result
							.getPosition()));
		} else if (result.getNumber() == null) {
			return null;
		}

		if (Integer.class.equals(boxedType)) {
			if (StringToNumberParserText.inIntegerRange(result.getNumber())) {
				return (T) Integer.valueOf(result.getNumber().intValue());
			}
		} else if (Double.class.equals(boxedType)) {
			if (StringToNumberParserText.inDoubleRange(result.getNumber())) {
				return (T) Double.valueOf(result.getNumber().doubleValue());
			}
		} else if (Long.class.equals(boxedType)) {
			if (StringToNumberParserText.inLongRange(result.getNumber())) {
				return (T) Long.valueOf(result.getNumber().longValue());
			}
		} else if (Float.class.equals(boxedType)) {
			if (StringToNumberParserText.inFloatRange(result.getNumber())) {
				return (T) Float.valueOf(result.getNumber().floatValue());
			}
		} else if (BigInteger.class.equals(boxedType)) {
			Number n = result.getNumber();
			if(n instanceof Long)
				return (T) BigInteger.valueOf(n.longValue());
			else if(n instanceof BigInteger)
				return (T) n;
			else if(n instanceof BigDecimal)
				return (T) ((BigDecimal) n).toBigInteger();
			else
				return (T) BigDecimal.valueOf(n.doubleValue()).toBigInteger();
		} else if (BigDecimal.class.equals(boxedType)) {
			Number n = result.getNumber();
			if(n instanceof Long)
				return (T) BigDecimal.valueOf(n.longValue());
			else if(n instanceof BigInteger)
				return (T) new BigDecimal((BigInteger) n);
			else if(n instanceof BigDecimal)
				return (T) n;
			else if(icuBigDecimal != null && icuBigDecimal.isInstance(n)) {
				try {
					int scale = ((Integer) icuBigDecimalScale.invoke(n)).intValue();
					BigInteger unscaledValue = (BigInteger) icuBigDecimalUnscaledValue.invoke(n);
					return (T) new java.math.BigDecimal(unscaledValue, scale);
				} catch(IllegalAccessException e) {
					throw new IllegalArgumentException("Error (IllegalAccessException) converting BigDecimal using ICU"); //$NON-NLS-1$
				} catch(InvocationTargetException e) {
					throw new IllegalArgumentException("Error (InvocationTargetException) converting BigDecimal using ICU"); //$NON-NLS-1$
				}
			} else if(n instanceof Double) {
				return (T) BigDecimal.valueOf(n.doubleValue());
			}
		} else if (Short.class.equals(boxedType)) {
			if (StringToNumberParserText.inShortRange(result.getNumber())) {
				return (T) Short.valueOf(result.getNumber().shortValue());
			}
		} else if (Byte.class.equals(boxedType)) {
			if (StringToNumberParserText.inByteRange(result.getNumber())) {
				return (T) Byte.valueOf(result.getNumber().byteValue());
			}
		}

		if (min != null && max != null) {
			throw new IllegalArgumentException(StringToNumberParserText
					.createOutOfRangeMessage(min, max, numberFormat));
		}

		throw new IllegalArgumentException(
				"Could not convert [" + fromObject + "] to type [" + toType + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public static StringToNumberConverter<Integer> toInteger(boolean primitive) {
		return toInteger(NumberFormat.getIntegerInstance(), primitive);
	}

	public static StringToNumberConverter<Integer> toInteger(NumberFormat numberFormat, boolean primitive) {
		return new StringToNumberConverter<>(numberFormat,
				(primitive) ? Integer.TYPE : Integer.class, MIN_INTEGER,
				MAX_INTEGER, Integer.class);
	}

	public static StringToNumberConverter<Double> toDouble(boolean primitive) {
		return toDouble(NumberFormat.getNumberInstance(), primitive);
	}

	public static StringToNumberConverter<Double> toDouble(NumberFormat numberFormat, boolean primitive) {
		return new StringToNumberConverter<>(numberFormat,
				(primitive) ? Double.TYPE : Double.class, MIN_DOUBLE,
				MAX_DOUBLE, Double.class);
	}

	public static StringToNumberConverter<Long> toLong(boolean primitive) {
		return toLong(NumberFormat.getIntegerInstance(), primitive);
	}

	public static StringToNumberConverter<Long> toLong(NumberFormat numberFormat, boolean primitive) {
		return new StringToNumberConverter<>(numberFormat,
				(primitive) ? Long.TYPE : Long.class, MIN_LONG, MAX_LONG,
				Long.class);
	}

	public static StringToNumberConverter<Float> toFloat(boolean primitive) {
		return toFloat(NumberFormat.getNumberInstance(), primitive);
	}

	public static StringToNumberConverter<Float> toFloat(NumberFormat numberFormat, boolean primitive) {
		return new StringToNumberConverter<>(numberFormat,
				(primitive) ? Float.TYPE : Float.class, MIN_FLOAT, MAX_FLOAT,
				Float.class);
	}

	public static StringToNumberConverter<BigInteger> toBigInteger() {
		return toBigInteger(NumberFormat.getIntegerInstance());
	}

	public static StringToNumberConverter<BigInteger> toBigInteger(NumberFormat numberFormat) {
		return new StringToNumberConverter<>(numberFormat, BigInteger.class,
				null, null, BigInteger.class);
	}

	public static StringToNumberConverter<BigDecimal> toBigDecimal() {
		return toBigDecimal(NumberFormat.getNumberInstance());
	}

	public static StringToNumberConverter<BigDecimal> toBigDecimal(NumberFormat numberFormat) {
		return new StringToNumberConverter<>(numberFormat, BigDecimal.class,
				null, null, BigDecimal.class);
	}

	public static StringToNumberConverter<Short> toShort(boolean primitive) {
		return toShort(NumberFormat.getIntegerInstance(), primitive);
	}

	public static StringToNumberConverter<Short> toShort(NumberFormat numberFormat, boolean primitive) {
		return new StringToNumberConverter<>(numberFormat,
				(primitive) ? Short.TYPE : Short.class, MIN_SHORT,
				MAX_SHORT, Short.class);
	}

	public static StringToNumberConverter<Byte> toByte(boolean primitive) {
		return toByte(NumberFormat.getIntegerInstance(), primitive);
	}

	public static StringToNumberConverter<Byte> toByte(NumberFormat numberFormat, boolean primitive) {
		return new StringToNumberConverter<>(numberFormat,
				(primitive) ? Byte.TYPE : Byte.class, MIN_BYTE,
				MAX_BYTE, Byte.class);
	}

}
