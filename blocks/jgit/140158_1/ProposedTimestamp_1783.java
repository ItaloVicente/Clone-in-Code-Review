
package org.eclipse.jgit.util.time;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class MonotonicSystemClock implements MonotonicClock {
	private static final AtomicLong before = new AtomicLong();

	private static long nowMicros() {
		long now = MILLISECONDS.toMicros(System.currentTimeMillis());
		for (;;) {
			long o = before.get();
			long n = Math.max(o + 1
			if (before.compareAndSet(o
				return n;
			}
		}
	}

	@Override
	public ProposedTimestamp propose() {
		final long u = nowMicros();
		return new ProposedTimestamp() {
			@Override
			public long read(TimeUnit unit) {
				return unit.convert(u
			}

			@Override
			public void blockUntil(Duration maxWait) {
			}
		};
	}
}
