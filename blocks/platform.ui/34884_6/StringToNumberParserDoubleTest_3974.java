
package org.eclipse.core.tests.internal.databinding.conversion;

import org.eclipse.core.internal.databinding.conversion.StringToNumberParser;

public class StringToNumberParserByteTest extends
		StringToNumberParserTestHarness {

	@Override
	protected boolean assertValid(Number number) {
		return StringToNumberParser.inByteRange(number);
	}

	@Override
	protected Number getValidMax() {
		return new Byte(Byte.MAX_VALUE);
	}

	@Override
	protected Number getValidMin() {
		return new Byte(Byte.MIN_VALUE);
	}
}
