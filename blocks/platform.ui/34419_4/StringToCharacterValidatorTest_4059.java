
package org.eclipse.core.tests.internal.databinding.validation;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.internal.databinding.conversion.StringToByteConverter;
import org.eclipse.core.internal.databinding.validation.StringToByteValidator;

import com.ibm.icu.text.NumberFormat;

public class StringToByteValidatorTest extends
		StringToNumberValidatorTestHarness {

	@Override
	protected Number getInRangeNumber() {
		return new Byte(Byte.MAX_VALUE);
	}

	@Override
	protected String getInvalidString() {
		return "1.1";
	}

	@Override
	protected Number getOutOfRangeNumber() {
		return new Integer(Byte.MAX_VALUE + 1);
	}

	@Override
	protected NumberFormat setupNumberFormat() {
		return NumberFormat.getIntegerInstance();
	}

	@Override
	protected IValidator setupValidator(NumberFormat numberFormat) {
		StringToByteConverter converter = StringToByteConverter.toByte(numberFormat, false);
		return new StringToByteValidator(converter);
	}
}
