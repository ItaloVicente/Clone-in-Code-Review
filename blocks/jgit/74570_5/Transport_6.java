
package org.eclipse.jgit.transport;

import java.util.List;

public abstract class PushOptionWrapper {
	protected List<String> pushOptions;

	public List<String> getPushOptions() {
		return pushOptions;
	}
}
