
package org.eclipse.core.internal.databinding.validation;

import java.text.NumberFormat;

import org.eclipse.core.databinding.conversion.Converter;

public abstract class NumberFormatConverterText<F, T extends Number> extends Converter<F, T> {
	private final NumberFormat numberFormat;

	public NumberFormatConverterText(Object fromType, Object toType, NumberFormat numberFormat) {
		super(fromType, toType);

		this.numberFormat = numberFormat;
	}

		return numberFormat;
	}
}
