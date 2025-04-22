
package org.eclipse.jgit.transport;

import java.util.List;

public class MockPushOptionWrapper extends PushOptionWrapper {
	public MockPushOptionWrapper setPushOptions(List<String> pushOptions) {
		this.pushOptions = pushOptions;
		return this;
	}
}
