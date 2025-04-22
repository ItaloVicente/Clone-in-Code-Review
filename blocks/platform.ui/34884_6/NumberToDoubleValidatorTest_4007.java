
package org.eclipse.core.tests.internal.databinding.validation;

import org.eclipse.core.internal.databinding.conversion.NumberToByteConverter;
import org.eclipse.core.internal.databinding.validation.NumberToByteValidator;
import org.eclipse.core.internal.databinding.validation.NumberToNumberValidator;

import com.ibm.icu.text.NumberFormat;

public class NumberToByteValidatorTest extends NumberToNumberValidatorTestHarness {
	@Override
	protected Number doGetOutOfRangeNumber() {
		return new Integer(Byte.MAX_VALUE + 1);
	}

	@Override
	protected NumberToNumberValidator doGetToBoxedTypeValidator(Class fromType) {
		NumberToByteConverter converter = new NumberToByteConverter(NumberFormat.getInstance(),
				fromType, false);
		return new NumberToByteValidator(converter);
	}

	@Override
	protected NumberToNumberValidator doGetToPrimitiveValidator(Class fromType) {
		NumberToByteConverter converter = new NumberToByteConverter(NumberFormat.getInstance(),
				fromType, true);
		return new NumberToByteValidator(converter);
	}
}
