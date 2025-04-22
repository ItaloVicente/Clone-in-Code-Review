				FileTime startTime = Files.getLastModifiedTime(probe);
				FileTime actTime = startTime;
				long sleepTime = 512;
				while (actTime.compareTo(startTime) <= 0) {
					TimeUnit.NANOSECONDS.sleep(sleepTime);
					if (timeout(start)) {
						LOG.warn(MessageFormat.format(JGitText
								.get().timeoutMeasureFsTimestampResolution,
								s.toString()));
						fsTimestampResolution = FALLBACK_TIMESTAMP_RESOLUTION;
						return;
					}
