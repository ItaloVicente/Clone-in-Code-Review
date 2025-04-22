
package org.eclipse.jgit.util.time;

import java.util.concurrent.TimeUnit;

public interface Clock {
	ProposedTimestamp propose();
}
