
package org.eclipse.core.tests.internal.databinding.validation;

import org.eclipse.core.internal.databinding.conversion.NumberToBigIntegerConverter;
import org.eclipse.core.internal.databinding.validation.NumberToNumberValidator;
import org.eclipse.core.internal.databinding.validation.NumberToUnboundedNumberValidator;

import com.ibm.icu.text.NumberFormat;

public class NumberToUnboundedNumberValidatorTest extends
		NumberToNumberValidatorTestHarness {

	@Override
	protected Number doGetOutOfRangeNumber() {
		return null;
	}

	@Override
	protected NumberToNumberValidator doGetToBoxedTypeValidator(Class fromType) {
		NumberToBigIntegerConverter converter = new NumberToBigIntegerConverter(NumberFormat.getInstance(), fromType);
		return new NumberToUnboundedNumberValidator(converter);
	}

	@Override
	protected NumberToNumberValidator doGetToPrimitiveValidator(Class fromType) {
		return null;  // primitive BigInteger does not exist
	}
}
