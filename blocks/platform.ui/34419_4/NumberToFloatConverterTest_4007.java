
package org.eclipse.core.tests.internal.databinding.conversion;

import java.math.BigDecimal;

import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.internal.databinding.conversion.NumberToDoubleConverter;

import com.ibm.icu.text.NumberFormat;

public class NumberToDoubleConverterTest extends NumberToNumberTestHarness {
	@Override
	protected Number doGetOutOfRangeNumber() {
		return new BigDecimal(Double.MAX_VALUE).add(new BigDecimal(Double.MAX_VALUE));
	}

	@Override
	protected IConverter doGetToBoxedTypeValidator(Class fromType) {
		return new NumberToDoubleConverter(NumberFormat.getInstance(), fromType, false);
	}

	@Override
	protected IConverter doGetToPrimitiveValidator(Class fromType) {
		return new NumberToDoubleConverter(NumberFormat.getInstance(), fromType, true);
	}

	@Override
	protected Class doGetToType(boolean primitive) {
		return (primitive) ? Double.TYPE : Double.class;
	}
}
