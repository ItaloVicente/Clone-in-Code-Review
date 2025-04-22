package org.eclipse.core.internal.databinding.conversion;

import org.eclipse.core.internal.databinding.conversion.StringToNumberParser.ParseResult;
import org.eclipse.core.internal.databinding.validation.NumberFormatConverter;

import java.text.NumberFormat;

public class StringToByteConverter2 extends NumberFormatConverter<Object, Byte> {
	private String outOfRangeMessage;
	private NumberFormat numberFormat;
	private boolean primitive;

	private StringToByteConverter2(NumberFormat numberFormat, Class<?> toType) {
		super(String.class, toType, numberFormat);
		primitive = toType.isPrimitive();
		this.numberFormat = numberFormat;
	}

	public static StringToByteConverter2 toByte(NumberFormat numberFormat, boolean primitive) {
		return new StringToByteConverter2(numberFormat, (primitive) ? Byte.TYPE : Byte.class);
	}

	public static StringToByteConverter2 toByte(boolean primitive) {
		return toByte(NumberFormat.getIntegerInstance(), primitive);
	}

	@Override
	public Byte convert(Object fromObject) {
		ParseResult result = StringToNumberParser.parse(fromObject,
				numberFormat, primitive);

		if (result.getPosition() != null) {
			throw new IllegalArgumentException(StringToNumberParser
					.createParseErrorMessage((String) fromObject, result
							.getPosition()));
		} else if (result.getNumber() == null) {
			return null;
		}

		if (StringToNumberParser.inByteRange(result.getNumber())) {
			return Byte.valueOf(result.getNumber().byteValue());
		}

		synchronized (this) {
			if (outOfRangeMessage == null) {
				outOfRangeMessage = StringToNumberParser
						.createOutOfRangeMessage(Byte.valueOf(Byte.MIN_VALUE), Byte.valueOf(Byte.MAX_VALUE),
								numberFormat);
			}

			throw new IllegalArgumentException(outOfRangeMessage);
		}
	}
}
