
package org.eclipse.jgit.util.time;

import java.time.Duration;

public interface MonotonicClock {
	ProposedTimestamp propose();
}
