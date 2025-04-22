
package org.eclipse.core.internal.databinding.conversion;

import java.text.NumberFormat;

import org.eclipse.core.internal.databinding.conversion.StringToNumberParser2.ParseResult;
import org.eclipse.core.internal.databinding.validation.NumberFormatConverter2;

public class StringToShortConverter2 extends NumberFormatConverter2<Object, Short> {
	private final NumberFormat numberFormat;
	private final boolean primitive;

	private String outOfRangeMessage;

	private StringToShortConverter2(NumberFormat numberFormat, Class<?> toType) {
		super(String.class, toType, numberFormat);
		this.numberFormat = numberFormat;
		primitive = toType.isPrimitive();
	}

	@Override
	public Short convert(Object fromObject) {
		ParseResult result = StringToNumberParser2.parse(fromObject,
				numberFormat, primitive);

		if (result.getPosition() != null) {
			throw new IllegalArgumentException(
					StringToNumberParser2
					.createParseErrorMessage((String) fromObject, result
							.getPosition()));
		} else if (result.getNumber() == null) {
			return null;
		}

		if (StringToNumberParser2.inShortRange(result.getNumber())) {
			return Short.valueOf(result.getNumber().shortValue());
		}

		synchronized (this) {
			if (outOfRangeMessage == null) {
				outOfRangeMessage = StringToNumberParser2
						.createOutOfRangeMessage(Short.valueOf(Short.MIN_VALUE), Short.valueOf(Short.MAX_VALUE),
								numberFormat);
			}

			throw new IllegalArgumentException(outOfRangeMessage);
		}
	}

	public static StringToShortConverter2 toShort(boolean primitive) {
		return toShort(NumberFormat.getIntegerInstance(), primitive);
	}

	public static StringToShortConverter2 toShort(NumberFormat numberFormat,
			boolean primitive) {
		return new StringToShortConverter2(numberFormat,
				(primitive) ? Short.TYPE : Short.class);
	}
}
