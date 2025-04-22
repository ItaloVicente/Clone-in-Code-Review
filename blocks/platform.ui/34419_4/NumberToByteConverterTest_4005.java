
package org.eclipse.core.tests.internal.databinding.conversion;

import java.math.BigInteger;

import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.internal.databinding.conversion.NumberToBigIntegerConverter;

import com.ibm.icu.text.NumberFormat;

public class NumberToBigIntegerConverterTest extends NumberToNumberTestHarness {
	private NumberFormat numberFormat;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected Number doGetOutOfRangeNumber() {
		return null;
	}

	@Override
	protected IConverter doGetToBoxedTypeValidator(Class fromType) {
		return new NumberToBigIntegerConverter(numberFormat, fromType);
	}

	@Override
	protected IConverter doGetToPrimitiveValidator(Class fromType) {
		return null;  //no such thing
	}

	@Override
	protected Class doGetToType(boolean primitive) {
		return (primitive) ? null : BigInteger.class;
	}
}
