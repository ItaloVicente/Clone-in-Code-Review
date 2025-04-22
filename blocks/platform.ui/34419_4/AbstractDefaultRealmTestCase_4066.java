
package org.eclipse.core.tests.internal.databinding.validation;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.internal.databinding.conversion.StringToShortConverter;
import org.eclipse.core.internal.databinding.validation.StringToShortValidator;

import com.ibm.icu.text.NumberFormat;

public class StringToShortValidatorTest extends
		StringToNumberValidatorTestHarness {

	@Override
	protected Number getInRangeNumber() {
		return new Short(Short.MAX_VALUE);
	}

	@Override
	protected String getInvalidString() {
		return "1.1";
	}

	@Override
	protected Number getOutOfRangeNumber() {
		return new Integer(Short.MAX_VALUE + 1);
	}

	@Override
	protected NumberFormat setupNumberFormat() {
		return NumberFormat.getIntegerInstance();
	}

	@Override
	protected IValidator setupValidator(NumberFormat numberFormat) {
		StringToShortConverter converter = StringToShortConverter.toShort(numberFormat, false);
		return new StringToShortValidator(converter);
	}

}
