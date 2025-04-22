
package org.eclipse.jgit.util.time;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public interface MonotonicClock {
	ProposedTimestamp propose();
}
