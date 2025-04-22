			long nanos = resolution.toNanos();
			TimeUnit unit;
			if (nanos < 200_000L) {
				unit = TimeUnit.NANOSECONDS;
			} else if (nanos < 200_000_000L) {
				unit = TimeUnit.MICROSECONDS;
			} else {
				unit = TimeUnit.MILLISECONDS;
			}
