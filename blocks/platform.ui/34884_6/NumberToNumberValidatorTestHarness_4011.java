
package org.eclipse.core.tests.internal.databinding.validation;

import org.eclipse.core.internal.databinding.conversion.NumberToLongConverter;
import org.eclipse.core.internal.databinding.validation.NumberToLongValidator;
import org.eclipse.core.internal.databinding.validation.NumberToNumberValidator;

import com.ibm.icu.text.NumberFormat;

public class NumberToLongValidatorTest extends
		NumberToNumberValidatorTestHarness {

	@Override
	protected Number doGetOutOfRangeNumber() {
		return new Double(Double.MAX_VALUE);
	}

	@Override
	protected NumberToNumberValidator doGetToBoxedTypeValidator(Class fromType) {
		NumberToLongConverter converter = new NumberToLongConverter(NumberFormat.getInstance(), fromType, false);
		return new NumberToLongValidator(converter);
	}

	@Override
	protected NumberToNumberValidator doGetToPrimitiveValidator(Class fromType) {
		NumberToLongConverter converter = new NumberToLongConverter(NumberFormat.getInstance(), fromType, true);
		return new NumberToLongValidator(converter);
	}
}
