
package org.eclipse.core.tests.internal.databinding.conversion;

import java.math.BigDecimal;

import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.internal.databinding.conversion.NumberToBigDecimalConverter;

import com.ibm.icu.text.NumberFormat;

public class NumberToBigDecimalTest extends NumberToNumberTestHarness {

	@Override
	protected Number doGetOutOfRangeNumber() {
		return null; //does not exist
	}

	@Override
	protected IConverter doGetToBoxedTypeValidator(Class fromType) {
		return new NumberToBigDecimalConverter(NumberFormat.getInstance(), fromType);
	}

	@Override
	protected IConverter doGetToPrimitiveValidator(Class fromType) {
		return null; // does not exist
	}

	@Override
	protected Class doGetToType(boolean primitive) {
		return (primitive) ? null : BigDecimal.class;
	}
}
