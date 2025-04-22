
package org.eclipse.core.tests.internal.databinding.validation;

import org.eclipse.core.internal.databinding.conversion.NumberToShortConverter;
import org.eclipse.core.internal.databinding.validation.NumberToNumberValidator;
import org.eclipse.core.internal.databinding.validation.NumberToShortValidator;

import com.ibm.icu.text.NumberFormat;

public class NumberToShortValidatorTest extends NumberToNumberValidatorTestHarness {
	@Override
	protected Number doGetOutOfRangeNumber() {
		return new Integer(Short.MAX_VALUE + 1);
	}

	@Override
	protected NumberToNumberValidator doGetToBoxedTypeValidator(Class fromType) {
		NumberToShortConverter converter = new NumberToShortConverter(NumberFormat.getInstance(),
				Integer.class, false);
		return new NumberToShortValidator(converter);
	}

	@Override
	protected NumberToNumberValidator doGetToPrimitiveValidator(Class fromType) {
		NumberToShortConverter converter = new NumberToShortConverter(NumberFormat.getInstance(),
				Integer.class, true);
		return new NumberToShortValidator(converter);
	}
}
