				String.format(
						"FileSnapshot: failures to detect file modifications"
								+ " {} out of {}\n"
								+ "timestamp resolution {} µs\n"
								+ "min racy threshold {} µs"
						
						fsAttrCache.getFsTimestampResolution().toNanos() / 1000
						fsAttrCache.getMinimalRacyInterval().toNanos() / 1000)
