
package org.eclipse.core.tests.internal.databinding.conversion;

import org.eclipse.core.internal.databinding.conversion.StringToNumberParser;

public class StringToNumberParserLongTest extends
		StringToNumberParserTestHarness {

	@Override
	protected boolean assertValid(Number number) {
		return StringToNumberParser.inLongRange(number);
	}

	@Override
	protected Number getValidMax() {
		return new Long(Long.MAX_VALUE);
	}

	@Override
	protected Number getValidMin() {
		return new Long(Long.MIN_VALUE);
	}
}
