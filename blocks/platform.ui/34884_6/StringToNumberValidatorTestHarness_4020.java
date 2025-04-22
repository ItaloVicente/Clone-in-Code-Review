
package org.eclipse.core.tests.internal.databinding.validation;

import org.eclipse.core.databinding.conversion.StringToNumberConverter;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.internal.databinding.validation.NumberFormatConverter;
import org.eclipse.core.internal.databinding.validation.StringToLongValidator;

import com.ibm.icu.text.NumberFormat;

public class StringToLongValidatorTest extends StringToNumberValidatorTestHarness {
	@Override
	protected Number getInRangeNumber() {
		return new Long(1);
	}

	@Override
	protected String getInvalidString() {
		return "1.1";
	}

	@Override
	protected Number getOutOfRangeNumber() {
		return new Double(Double.MAX_VALUE);
	}

	@Override
	protected NumberFormat setupNumberFormat() {
		return NumberFormat.getIntegerInstance();
	}

	@Override
	protected IValidator setupValidator(NumberFormat numberFormat) {
		NumberFormatConverter converter = StringToNumberConverter.toInteger(numberFormat, false);
		return new StringToLongValidator(converter);
	}
}
