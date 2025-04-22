package org.eclipse.jgit.transport;

import java.util.List;

public class MockOptionStringWrapper extends OptionStringWrapper {
	public MockOptionStringWrapper setOptionStrings(
			List<String> optionStrings) {
		this.optionStrings = optionStrings;
		return this;
	}
}
