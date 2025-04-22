
package org.eclipse.core.tests.internal.databinding.validation;

import java.math.BigDecimal;

import org.eclipse.core.databinding.conversion.StringToNumberConverter;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.internal.databinding.validation.StringToDoubleValidator;

import com.ibm.icu.text.NumberFormat;

public class StringToDoubleValidatorTest extends
		StringToNumberValidatorTestHarness {

	@Override
	protected Number getInRangeNumber() {
		return new Double(1);
	}

	@Override
	protected String getInvalidString() {
		return "1a";
	}

	@Override
	protected Number getOutOfRangeNumber() {
		BigDecimal decimal = new BigDecimal(Double.MAX_VALUE);
		return decimal.add(new BigDecimal(Double.MAX_VALUE));
	}

	@Override
	protected NumberFormat setupNumberFormat() {
		return NumberFormat.getInstance();
	}

	@Override
	protected IValidator setupValidator(NumberFormat numberFormat) {
		StringToNumberConverter converter = StringToNumberConverter.toDouble(numberFormat, false);
		return new StringToDoubleValidator(converter);
	}
}
