					if (duration > maxGC) {
						if (Policy.DEBUG_GC) {
						}
						shutdown();
					} else {
						nextGCInterval = Math.max(minGCInterval, GC_DELAY_MULTIPLIER * duration);
						if (Policy.DEBUG_GC) {
						}
