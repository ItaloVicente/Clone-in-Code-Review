
package org.eclipse.core.databinding.conversion.text;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;

import org.eclipse.core.databinding.conversion.Converter;

public class NumberToStringConverter extends Converter<Object, String> {
	private final NumberFormat numberFormat;
	private final Class<?> fromType;
	private boolean fromTypeFitsLong;
	private boolean fromTypeIsDecimalType;
	private boolean fromTypeIsBigInteger;
	private boolean fromTypeIsBigDecimal;

	static Class<?> icuBigDecimal = null;
	static Constructor<?> icuBigDecimalCtr = null;

	private NumberToStringConverter(NumberFormat numberFormat, Class<?> fromType) {
		super(fromType, String.class);

		this.numberFormat = Objects.requireNonNull(numberFormat);
		this.fromType = Objects.requireNonNull(fromType);

		if (Integer.class.equals(fromType) || Integer.TYPE.equals(fromType) || Long.class.equals(fromType)
				|| Long.TYPE.equals(fromType) || Short.class.equals(fromType) || Short.TYPE.equals(fromType)
				|| Byte.class.equals(fromType) || Byte.TYPE.equals(fromType)) {
			fromTypeFitsLong = true;
		} else if (Float.class.equals(fromType) || Float.TYPE.equals(fromType) || Double.class.equals(fromType)
				|| Double.TYPE.equals(fromType)) {
			fromTypeIsDecimalType = true;
		} else if (BigInteger.class.equals(fromType)) {
			fromTypeIsBigInteger = true;
		} else if (BigDecimal.class.equals(fromType)) {
			fromTypeIsBigDecimal = true;
		}
	}

	@Override
	public String convert(Object fromObject) {
		if (fromObject == null && !fromType.isPrimitive()) {
			return ""; //$NON-NLS-1$
		}

		Number number = (Number) fromObject;
		String result = null;
		if (fromTypeFitsLong) {
			synchronized (numberFormat) {
				result = numberFormat.format(number.longValue());
			}
		} else if (fromTypeIsDecimalType) {
			synchronized (numberFormat) {
				result = numberFormat.format(number.doubleValue());
			}
		} else if (fromTypeIsBigInteger) {
			synchronized (numberFormat) {
				result = numberFormat.format(number);
			}
		} else if (fromTypeIsBigDecimal) {
			if (icuBigDecimal != null && icuBigDecimalCtr != null && numberFormat instanceof DecimalFormat) {
				BigDecimal o = (BigDecimal) fromObject;
				try {
					fromObject = icuBigDecimalCtr.newInstance(o.unscaledValue(), Integer.valueOf(o.scale()));
				} catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
				}
			}
			synchronized (numberFormat) {
				result = numberFormat.format(fromObject);
			}
		}

		return result;
	}

	public static NumberToStringConverter fromDouble(boolean primitive) {
		return fromDouble(NumberFormat.getNumberInstance(), primitive);
	}

	public static NumberToStringConverter fromDouble(NumberFormat numberFormat, boolean primitive) {
		return new NumberToStringConverter(numberFormat, (primitive) ? Double.TYPE : Double.class);
	}

	public static NumberToStringConverter fromLong(boolean primitive) {
		return fromLong(NumberFormat.getIntegerInstance(), primitive);
	}

	public static NumberToStringConverter fromLong(NumberFormat numberFormat, boolean primitive) {
		return new NumberToStringConverter(numberFormat, (primitive) ? Long.TYPE : Long.class);
	}

	public static NumberToStringConverter fromFloat(boolean primitive) {
		return fromFloat(NumberFormat.getNumberInstance(), primitive);
	}

	public static NumberToStringConverter fromFloat(NumberFormat numberFormat, boolean primitive) {
		return new NumberToStringConverter(numberFormat, (primitive) ? Float.TYPE : Float.class);
	}

	public static NumberToStringConverter fromInteger(boolean primitive) {
		return fromInteger(NumberFormat.getIntegerInstance(), primitive);
	}

	public static NumberToStringConverter fromInteger(NumberFormat numberFormat, boolean primitive) {
		return new NumberToStringConverter(numberFormat, (primitive) ? Integer.TYPE : Integer.class);
	}

	public static NumberToStringConverter fromBigInteger() {
		return fromBigInteger(NumberFormat.getIntegerInstance());
	}

	public static NumberToStringConverter fromBigInteger(NumberFormat numberFormat) {
		return new NumberToStringConverter(numberFormat, BigInteger.class);
	}

	public static NumberToStringConverter fromBigDecimal() {
		return fromBigDecimal(NumberFormat.getNumberInstance());
	}

	public static NumberToStringConverter fromBigDecimal(NumberFormat numberFormat) {
		return new NumberToStringConverter(numberFormat, BigDecimal.class);
	}

	public static NumberToStringConverter fromShort(boolean primitive) {
		return fromShort(NumberFormat.getIntegerInstance(), primitive);
	}

	public static NumberToStringConverter fromShort(NumberFormat numberFormat, boolean primitive) {
		return new NumberToStringConverter(numberFormat, (primitive) ? Short.TYPE : Short.class);
	}

	public static NumberToStringConverter fromByte(boolean primitive) {
		return fromByte(NumberFormat.getIntegerInstance(), primitive);
	}

	public static NumberToStringConverter fromByte(NumberFormat numberFormat, boolean primitive) {
		return new NumberToStringConverter(numberFormat, (primitive) ? Byte.TYPE : Byte.class);
	}

}
