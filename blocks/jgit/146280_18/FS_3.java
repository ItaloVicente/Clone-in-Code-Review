			long resolution = c.getFsTimestampResolution().toNanos();
			TimeUnit resolutionUnit = getUnit(resolution);
			long resolutionValue = resolutionUnit.convert(resolution
					TimeUnit.NANOSECONDS);

			long minRacyThreshold = c.getMinimalRacyInterval().toNanos();
			TimeUnit minRacyThresholdUnit = getUnit(minRacyThreshold);
			long minRacyThresholdValue = minRacyThresholdUnit
					.convert(minRacyThreshold
