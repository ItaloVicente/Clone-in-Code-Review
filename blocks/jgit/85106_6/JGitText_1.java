
package org.eclipse.jgit.junit.time;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.util.time.MonotonicClock;
import org.eclipse.jgit.util.time.ProposedTimestamp;

public class MonotonicFakeClock implements MonotonicClock {
	private long now = TimeUnit.SECONDS.toMicros(42);

	public void tick(long add
		if (add <= 0) {
			throw new IllegalArgumentException();
		}
		now += unit.toMillis(add);
	}

	@Override
	public ProposedTimestamp propose() {
		long t = now++;
		return new ProposedTimestamp() {
			@Override
			public long read(TimeUnit unit) {
				return unit.convert(t
			}

			@Override
			public void blockUntil(long timeout
					throws InterruptedException
			}
		};
	}
}
