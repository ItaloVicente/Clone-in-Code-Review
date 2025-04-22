
package org.eclipse.core.tests.internal.databinding.validation;

import java.math.BigDecimal;

import org.eclipse.core.internal.databinding.conversion.NumberToDoubleConverter;
import org.eclipse.core.internal.databinding.validation.NumberToDoubleValidator;
import org.eclipse.core.internal.databinding.validation.NumberToNumberValidator;

import com.ibm.icu.text.NumberFormat;

public class NumberToDoubleValidatorTest extends
		NumberToNumberValidatorTestHarness {

	@Override
	protected Number doGetOutOfRangeNumber() {
		return new BigDecimal(Double.MAX_VALUE).add(new BigDecimal(Double.MAX_VALUE));
	}

	@Override
	protected NumberToNumberValidator doGetToBoxedTypeValidator(Class fromType) {
		NumberToDoubleConverter converter = new NumberToDoubleConverter(NumberFormat.getInstance(), fromType, false);
		return new NumberToDoubleValidator(converter);
	}

	@Override
	protected NumberToNumberValidator doGetToPrimitiveValidator(Class fromType) {
		NumberToDoubleConverter converter = new NumberToDoubleConverter(NumberFormat.getInstance(), fromType, true);
		return new NumberToDoubleValidator(converter);
	}
}
