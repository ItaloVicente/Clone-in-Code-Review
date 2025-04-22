									if (c.fsTimestampResolution
											.toNanos() < 100_000_000L) {
										c.minimalRacyInterval = measureMinimalRacyInterval(
											dir);
									}
									attributeCache.put(s
