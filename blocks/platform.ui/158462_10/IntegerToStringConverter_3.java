package org.eclipse.core.internal.databinding.conversion;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Format;
import java.util.Objects;

import org.eclipse.core.internal.databinding.conversion.StringToNumberParser.ParseResult;
import org.eclipse.core.internal.databinding.validation.NumberFormatConverter;

public class AbstractStringToNumberConverter<T extends Number> extends NumberFormatConverter<Object, T> {
	private Class<?> toType;
	private Format numberFormat;

	private final Number min;
	private final Number max;

	private final Class<?> boxedType;

	protected static final Integer MIN_INTEGER = Integer.valueOf(Integer.MIN_VALUE);
	protected static final Integer MAX_INTEGER = Integer.valueOf(Integer.MAX_VALUE);

	protected static final Double MIN_DOUBLE = Double.valueOf(-Double.MAX_VALUE);
	protected static final Double MAX_DOUBLE = Double.valueOf(Double.MAX_VALUE);

	protected static final Long MIN_LONG = Long.valueOf(Long.MIN_VALUE);
	protected static final Long MAX_LONG = Long.valueOf(Long.MAX_VALUE);

	protected static final Float MIN_FLOAT = Float.valueOf(-Float.MAX_VALUE);
	protected static final Float MAX_FLOAT = Float.valueOf(Float.MAX_VALUE);

	protected static final Short MIN_SHORT = Short.valueOf(Short.MIN_VALUE);
	protected static final Short MAX_SHORT = Short.valueOf(Short.MAX_VALUE);

	protected static final Byte MIN_BYTE = Byte.valueOf(Byte.MIN_VALUE);
	protected static final Byte MAX_BYTE = Byte.valueOf(Byte.MAX_VALUE);

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

	protected AbstractStringToNumberConverter(Format numberFormat, Class<T> toType, Number min, Number max,
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
		ParseResult result = StringToNumberParser.parse(fromObject,
				numberFormat, toType.isPrimitive());

		if (result.getPosition() != null) {
			throw new IllegalArgumentException(StringToNumberParser
					.createParseErrorMessage((String) fromObject, result
							.getPosition()));
		} else if (result.getNumber() == null) {
			return null;
		}

		if (Integer.class.equals(boxedType)) {
			if (StringToNumberParser.inIntegerRange(result.getNumber())) {
				return (T) Integer.valueOf(result.getNumber().intValue());
			}
		} else if (Double.class.equals(boxedType)) {
			if (StringToNumberParser.inDoubleRange(result.getNumber())) {
				return (T) Double.valueOf(result.getNumber().doubleValue());
			}
		} else if (Long.class.equals(boxedType)) {
			if (StringToNumberParser.inLongRange(result.getNumber())) {
				return (T) Long.valueOf(result.getNumber().longValue());
			}
		} else if (Float.class.equals(boxedType)) {
			if (StringToNumberParser.inFloatRange(result.getNumber())) {
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
}
