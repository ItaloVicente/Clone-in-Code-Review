
package org.eclipse.core.tests.internal.databinding.validation;

import org.eclipse.core.internal.databinding.conversion.NumberToFloatConverter;
import org.eclipse.core.internal.databinding.validation.NumberToFloatValidator;
import org.eclipse.core.internal.databinding.validation.NumberToNumberValidator;

import com.ibm.icu.text.NumberFormat;

public class NumberToFloatValidatorTest extends
		NumberToNumberValidatorTestHarness {

	@Override
	protected Number doGetOutOfRangeNumber() {
		return new Double(Double.MAX_VALUE);
	}

	@Override
	protected NumberToNumberValidator doGetToBoxedTypeValidator(Class fromType) {
		NumberToFloatConverter converter = new NumberToFloatConverter(NumberFormat.getInstance(), fromType, false);
		return new NumberToFloatValidator(converter);
	}

	@Override
	protected NumberToNumberValidator doGetToPrimitiveValidator(Class fromType) {
		NumberToFloatConverter converter = new NumberToFloatConverter(NumberFormat.getInstance(), fromType, true);
		return new NumberToFloatValidator(converter);
	}
}
