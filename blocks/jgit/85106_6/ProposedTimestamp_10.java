
package org.eclipse.jgit.util.time;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class MonotonicSystemClock implements MonotonicClock {
	private static final AtomicLong before = new AtomicLong();

	private static long read() {
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
		long t = read();
		return new ProposedTimestamp() {
			@Override
			public long read(TimeUnit unit) {
				return unit.convert(t
			}

			@Override
			public void blockUntil(long d
			}
		};
	}
}
