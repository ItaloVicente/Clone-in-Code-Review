				Duration estimate;
				try {
					estimate = measureFSTimestampResolution(s
							Duration.ofNanos(512)
				} catch (TimeoutException e) {
					estimate = FALLBACK_TIMESTAMP_RESOLUTION;
				}
				if (estimate.compareTo(Duration.ofMillis(100)) > 0) {
					 fsTimestampResolution = estimate;
					 return;
				}
				Set<Duration> durations = new HashSet<>();
				durations.add(estimate);
				int count = 1;
				int n = 9;
				Duration initialSleep = estimate.dividedBy(5).multipliedBy(4);
				for (int i = 0; i < n; i++) {
					Duration d = null;
					try {
						d = measureFSTimestampResolution(s
								initialSleep
					} catch (TimeoutException e) {
						continue;
