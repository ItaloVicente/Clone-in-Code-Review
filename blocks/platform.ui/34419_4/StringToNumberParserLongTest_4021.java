
package org.eclipse.core.tests.internal.databinding.conversion;

import org.eclipse.core.internal.databinding.conversion.StringToNumberParser;

public class StringToNumberParserIntegerTest extends
		StringToNumberParserTestHarness {

	@Override
	protected boolean assertValid(Number number) {
		return StringToNumberParser.inIntegerRange(number);
	}

	@Override
	protected Number getValidMax() {
		return new Integer(Integer.MAX_VALUE);
	}

	@Override
	protected Number getValidMin() {
		return new Integer(Integer.MIN_VALUE);
	}
}
