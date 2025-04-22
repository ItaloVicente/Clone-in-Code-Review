
package org.eclipse.jgit.util.time;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.internal.JGitText;

public interface Clock {
	ProposedTimestamp propose();

	public default long now() {
		try (ProposedTimestamp p = propose()) {
			p.blockUntil(5
			return p.getMillis();
		} catch (InterruptedException | TimeoutException e) {
			throw new IllegalStateException(JGitText.get().timeIsUncertain
		}
	}
}
