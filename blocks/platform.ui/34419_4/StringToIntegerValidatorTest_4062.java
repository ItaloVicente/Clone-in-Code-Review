
package org.eclipse.core.tests.internal.databinding.validation;

import org.eclipse.core.databinding.conversion.StringToNumberConverter;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.internal.databinding.validation.StringToFloatValidator;

import com.ibm.icu.text.NumberFormat;

public class StringToFloatValidatorTest extends
		StringToNumberValidatorTestHarness {

	@Override
	protected Number getInRangeNumber() {
		return new Float(1);
	}

	@Override
	protected String getInvalidString() {
		return "1a";
	}

	@Override
	protected Number getOutOfRangeNumber() {
		return new Double(Double.MAX_VALUE);
	}

	@Override
	protected NumberFormat setupNumberFormat() {
		return NumberFormat.getInstance();
	}

	@Override
	protected IValidator setupValidator(NumberFormat numberFormat) {
		StringToNumberConverter converter = StringToNumberConverter.toFloat(numberFormat, false);
		return new StringToFloatValidator(converter);
	}
}
