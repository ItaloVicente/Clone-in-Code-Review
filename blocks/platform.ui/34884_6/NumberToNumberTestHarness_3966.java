
package org.eclipse.core.tests.internal.databinding.conversion;

import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.internal.databinding.conversion.NumberToLongConverter;

import com.ibm.icu.text.NumberFormat;

public class NumberToLongConverterTest extends NumberToNumberTestHarness {
	private NumberFormat numberFormat;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		numberFormat = NumberFormat.getInstance();
	}
	
	@Override
	protected Number doGetOutOfRangeNumber() {
		return new Double(Double.MAX_VALUE);
	}

	@Override
	protected IConverter doGetToBoxedTypeValidator(Class fromType) {
		return new NumberToLongConverter(numberFormat, fromType, false);
	}

	@Override
	protected IConverter doGetToPrimitiveValidator(Class fromType) {
		return new NumberToLongConverter(numberFormat, fromType, true);
	}

	@Override
	protected Class doGetToType(boolean primitive) {
		return (primitive) ? Long.TYPE : Long.class;
	}
}
