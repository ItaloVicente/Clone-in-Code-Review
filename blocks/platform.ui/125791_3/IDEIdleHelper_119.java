					if (duration > maxGC) {
						if (Policy.DEBUG_GC) {
							System.out.println("Further explicit GCs disabled due to long GC"); //$NON-NLS-1$
						}
						shutdown();
					} else {
						nextGCInterval = Math.max(minGCInterval, GC_DELAY_MULTIPLIER * duration);
						if (Policy.DEBUG_GC) {
							System.out.println("Next GC to run in: " + nextGCInterval); //$NON-NLS-1$
						}
