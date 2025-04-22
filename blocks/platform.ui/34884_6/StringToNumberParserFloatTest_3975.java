
package org.eclipse.core.tests.internal.databinding.conversion;

import org.eclipse.core.internal.databinding.conversion.StringToNumberParser;

public class StringToNumberParserDoubleTest extends
		StringToNumberParserTestHarness {

	@Override
	protected boolean assertValid(Number number) {
		return StringToNumberParser.inDoubleRange(number);
	}

	@Override
	protected Number getValidMax() {
		return new Double(Double.MAX_VALUE);
	}

	@Override
	protected Number getValidMin() {
		return new Double(-Double.MAX_VALUE);
	}
}
