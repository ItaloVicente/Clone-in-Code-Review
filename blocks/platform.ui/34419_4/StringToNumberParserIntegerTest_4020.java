
package org.eclipse.core.tests.internal.databinding.conversion;

import org.eclipse.core.internal.databinding.conversion.StringToNumberParser;

public class StringToNumberParserFloatTest extends
		StringToNumberParserTestHarness {

	@Override
	protected boolean assertValid(Number number) {
		return StringToNumberParser.inFloatRange(number);
	}

	@Override
	protected Number getValidMax() {
		return new Float(Float.MAX_VALUE);
	}

	@Override
	protected Number getValidMin() {
		return new Float(-Float.MAX_VALUE);
	}

}
