package org.eclipse.core.internal.databinding.conversion;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Format;
import java.util.Objects;

import org.eclipse.core.databinding.conversion.Converter;

public class AbstractNumberToStringConverter extends Converter<Object, String> {
	private final Format numberFormat;
	private final Class<?> fromType;
	private boolean fromTypeFitsLong;
	private boolean fromTypeIsDecimalType;
	private boolean fromTypeIsBigInteger;
	private boolean fromTypeIsBigDecimal;

	static Class<?> icuBigDecimal = null;
	static Constructor<?> icuBigDecimalCtr = null;
	static Class<?> icuDecimalFormat = null;

	{
		try {
			icuBigDecimal = Class.forName("com.ibm.icu.math.BigDecimal"); //$NON-NLS-1$
			icuBigDecimalCtr = icuBigDecimal.getConstructor(BigInteger.class, int.class);
			icuDecimalFormat = Class.forName("com.ibm.icu.text.DecimalFormat"); //$NON-NLS-1$
		} catch (ClassNotFoundException | NoSuchMethodException e) {
		}
	}

	protected AbstractNumberToStringConverter(Format numberFormat, Class<?> fromType) {
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
			if (icuBigDecimal != null && icuBigDecimalCtr != null && icuDecimalFormat.isInstance(numberFormat)) {
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
}
