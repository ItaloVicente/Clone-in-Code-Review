				String.format(
						"FileSnapshot: failures to detect file modifications"
								+ " %d out of %d\n"
								+ "timestamp resolution %d µs"
								+ " min racy threshold %d µs"
						
						fsAttrCache.getFsTimestampResolution().toNanos() / 1000
						fsAttrCache.getMinimalRacyInterval().toNanos() / 1000)
