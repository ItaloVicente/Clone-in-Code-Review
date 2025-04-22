				Duration wait = Duration.ofNanos(512);
				Duration estimate = singleMeasureTimestampResolution(s
						wait
				if (estimate.compareTo(Duration.ofMillis(100)) > 0) {
					resolution = estimate;
				} else {
					Set<Duration> durations = new HashSet<>();
					durations.add(estimate);
					Duration initialSleep = estimate.dividedBy(5)
							.multipliedBy(4);
					for (int i = 0; i < 10; i++) {
						Duration d = null;
						try {
							d = singleMeasureTimestampResolution(s
									initialSleep
						} catch (TimeoutException e) {
							continue;
						}
						if (d != null) {
							durations.add(d);
						}
