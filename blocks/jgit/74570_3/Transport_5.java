package org.eclipse.jgit.transport;

import java.util.List;

public abstract class OptionStringWrapper {
	protected List<String> optionStrings;

	public List<String> getOptionStrings() {
		return optionStrings;
	}
}
