
package org.eclipse.core.internal.databinding.validation;

import org.eclipse.core.databinding.conversion.Converter;

import java.text.NumberFormat;

public abstract class NumberFormatConverter2<F, T extends Number> extends Converter<F, T> {
	private final NumberFormat numberFormat;

	public NumberFormatConverter2(Object fromType, Object toType, NumberFormat numberFormat) {
		super(fromType, toType);

		this.numberFormat = numberFormat;
	}

		return numberFormat;
	}
}
