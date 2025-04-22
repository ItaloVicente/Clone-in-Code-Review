
package org.eclipse.jgit.fnmatch;

import java.util.List;

final class LastHead implements Head {
	static final Head INSTANCE = new LastHead();

	private LastHead() {
	}

	@Override
	public List<Head> getNextHeads(char c) {
		return FileNameMatcher.EMPTY_HEAD_LIST;
	}

}
