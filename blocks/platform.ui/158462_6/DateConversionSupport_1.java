
package org.eclipse.core.databinding.conversion;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Objects;

import org.eclipse.core.internal.databinding.conversion.StringToNumberParser;
import org.eclipse.core.internal.databinding.conversion.StringToNumberParser2;
import org.eclipse.core.internal.databinding.conversion.StringToNumberParser2.ParseResult;
import org.eclipse.core.internal.databinding.validation.NumberFormatConverter2;

public class StringToNumberConverter2<T extends Number> extends NumberFormatConverter2<Object, T> {
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

	private StringToNumberConverter2(NumberFormat numberFormat, Class<T> toType, Number min, Number max,
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
		ParseResult result = StringToNumberParser2.parse(fromObject,
				numberFormat, toType.isPrimitive());

		if (result.getPosition() != null) {
			throw new IllegalArgumentException(
					StringToNumberParser2
					.createParseErrorMessage((String) fromObject, result
							.getPosition()));
		} else if (result.getNumber() == null) {
			return null;
		}

		if (Integer.class.equals(boxedType)) {
			if (StringToNumberParser2.inIntegerRange(result.getNumber())) {
				return (T) Integer.valueOf(result.getNumber().intValue());
			}
		} else if (Double.class.equals(boxedType)) {
			if (StringToNumberParser2.inDoubleRange(result.getNumber())) {
				return (T) Double.valueOf(result.getNumber().doubleValue());
			}
		} else if (Long.class.equals(boxedType)) {
			if (StringToNumberParser2.inLongRange(result.getNumber())) {
				return (T) Long.valueOf(result.getNumber().longValue());
			}
		} else if (Float.class.equals(boxedType)) {
			if (StringToNumberParser2.inFloatRange(result.getNumber())) {
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
				BigDecimal bd = BigDecimal.valueOf(n.doubleValue());
				if (bd.scale() == 0)
					return (T) bd;
				throw new IllegalArgumentException("Non-integral Double value returned from NumberFormat " + //$NON-NLS-1$
						"which cannot be accurately stored in a BigDecimal due to lost precision. " + //$NON-NLS-1$
						"Consider using ICU4J or Java 5 which can properly format and parse these types."); //$NON-NLS-1$
			}
		} else if (Short.class.equals(boxedType)) {
			if (StringToNumberParser.inShortRange(result.getNumber())) {
				return (T) Short.valueOf(result.getNumber().shortValue());
			}
		} else if (Byte.class.equals(boxedType)) {
			if (StringToNumberParser.inByteRange(result.getNumber())) {
				return (T) Byte.valueOf(result.getNumber().byteValue());
			}
		}

		if (min != null && max != null) {
			throw new IllegalArgumentException(StringToNumberParser
					.createOutOfRangeMessage(min, max, numberFormat));
		}

		throw new IllegalArgumentException(
				"Could not convert [" + fromObject + "] to type [" + toType + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public static StringToNumberConverter2<Integer> toInteger(boolean primitive) {
		return toInteger(NumberFormat.getIntegerInstance(), primitive);
	}

	public static StringToNumberConverter2<Integer> toInteger(NumberFormat numberFormat, boolean primitive) {
		return new StringToNumberConverter2<>(numberFormat,
				(primitive) ? Integer.TYPE : Integer.class, MIN_INTEGER,
				MAX_INTEGER, Integer.class);
	}

	public static StringToNumberConverter2<Double> toDouble(boolean primitive) {
		return toDouble(NumberFormat.getNumberInstance(), primitive);
	}

	public static StringToNumberConverter2<Double> toDouble(NumberFormat numberFormat, boolean primitive) {
		return new StringToNumberConverter2<>(numberFormat,
				(primitive) ? Double.TYPE : Double.class, MIN_DOUBLE,
				MAX_DOUBLE, Double.class);
	}

	public static StringToNumberConverter2<Long> toLong(boolean primitive) {
		return toLong(NumberFormat.getIntegerInstance(), primitive);
	}

	public static StringToNumberConverter2<Long> toLong(NumberFormat numberFormat, boolean primitive) {
		return new StringToNumberConverter2<>(numberFormat,
				(primitive) ? Long.TYPE : Long.class, MIN_LONG, MAX_LONG,
				Long.class);
	}

	public static StringToNumberConverter2<Float> toFloat(boolean primitive) {
		return toFloat(NumberFormat.getNumberInstance(), primitive);
	}

	public static StringToNumberConverter2<Float> toFloat(NumberFormat numberFormat, boolean primitive) {
		return new StringToNumberConverter2<>(numberFormat,
				(primitive) ? Float.TYPE : Float.class, MIN_FLOAT, MAX_FLOAT,
				Float.class);
	}

	public static StringToNumberConverter2<BigInteger> toBigInteger() {
		return toBigInteger(NumberFormat.getIntegerInstance());
	}

	public static StringToNumberConverter2<BigInteger> toBigInteger(NumberFormat numberFormat) {
		return new StringToNumberConverter2<>(numberFormat, BigInteger.class,
				null, null, BigInteger.class);
	}

	public static StringToNumberConverter2<BigDecimal> toBigDecimal() {
		return toBigDecimal(NumberFormat.getNumberInstance());
	}

	public static StringToNumberConverter2<BigDecimal> toBigDecimal(NumberFormat numberFormat) {
		return new StringToNumberConverter2<>(numberFormat, BigDecimal.class,
				null, null, BigDecimal.class);
	}

	public static StringToNumberConverter2<Short> toShort(boolean primitive) {
		return toShort(NumberFormat.getIntegerInstance(), primitive);
	}

	public static StringToNumberConverter2<Short> toShort(NumberFormat numberFormat, boolean primitive) {
		return new StringToNumberConverter2<>(numberFormat,
				(primitive) ? Short.TYPE : Short.class, MIN_SHORT,
				MAX_SHORT, Short.class);
	}

	public static StringToNumberConverter2<Byte> toByte(boolean primitive) {
		return toByte(NumberFormat.getIntegerInstance(), primitive);
	}

	public static StringToNumberConverter2<Byte> toByte(NumberFormat numberFormat, boolean primitive) {
		return new StringToNumberConverter2<>(numberFormat,
				(primitive) ? Byte.TYPE : Byte.class, MIN_BYTE,
				MAX_BYTE, Byte.class);
	}

}
