
package org.eclipse.core.internal.databinding.conversion;

import org.eclipse.core.databinding.conversion.Converter;

import java.text.NumberFormat;

public class IntegerToStringConverter2 extends Converter<Object, String> {
	private final boolean primitive;
	private final NumberFormat numberFormat;
	private final Class<?> boxedType;

	private IntegerToStringConverter2(NumberFormat numberFormat, Class<?> fromType, Class<?> boxedType) {
		super(fromType, String.class);
		this.primitive = fromType.isPrimitive();
		this.numberFormat = numberFormat;
		this.boxedType = boxedType;
	}

	@Override
	public String convert(Object fromObject) {
		if (fromObject == null && !primitive) {
			return ""; //$NON-NLS-1$
		}

		if (!boxedType.isInstance(fromObject)) {
			throw new IllegalArgumentException(
					"'fromObject' is not of type [" + boxedType + "]."); //$NON-NLS-1$//$NON-NLS-2$
		}

		return numberFormat.format(((Number) fromObject).longValue());
	}

	public static IntegerToStringConverter2 fromShort(boolean primitive) {
		return fromShort(NumberFormat.getIntegerInstance(), primitive);
	}

	public static IntegerToStringConverter2 fromShort(NumberFormat numberFormat,
			boolean primitive) {
		return new IntegerToStringConverter2(numberFormat,
				primitive ? Short.TYPE : Short.class, Short.class);
	}

	public static IntegerToStringConverter2 fromByte(boolean primitive) {
		return fromByte(NumberFormat.getIntegerInstance(), primitive);
	}

	public static IntegerToStringConverter2 fromByte(NumberFormat numberFormat,
			boolean primitive) {
		return new IntegerToStringConverter2(numberFormat, primitive ? Byte.TYPE
				: Byte.class, Byte.class);
	}
}
