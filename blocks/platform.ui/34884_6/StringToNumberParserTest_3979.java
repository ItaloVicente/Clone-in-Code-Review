
package org.eclipse.core.tests.internal.databinding.conversion;

import org.eclipse.core.internal.databinding.conversion.StringToNumberParser;


public class StringToNumberParserShortTest extends StringToNumberParserTestHarness {

	@Override
	protected boolean assertValid(Number number) {
		return StringToNumberParser.inShortRange(number);
	}

	@Override
	protected Number getValidMax() {
		return new Short(Short.MAX_VALUE);
	}

	@Override
	protected Number getValidMin() {
		return new Short(Short.MIN_VALUE);
	}	
}
