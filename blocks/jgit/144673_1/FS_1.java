		Duration measureFSTimestampResolution(FileStore s
				Duration initialSleep
				throws IOException
			FileUtils.touch(probe);
			long start = System.nanoTime();
			FileTime startTime = Files.getLastModifiedTime(probe);
			FileTime actTime = startTime;
			long sleepTime = initialSleep.toNanos();
			while (actTime.compareTo(startTime) <= 0) {
				TimeUnit.NANOSECONDS.sleep(sleepTime);
				if (timeout(start)) {
					LOG.warn(MessageFormat.format(JGitText
							.get().timeoutMeasureFsTimestampResolution
							s.toString()));
					throw new TimeoutException();
				}
				FileUtils.touch(probe);
				actTime = Files.getLastModifiedTime(probe);
				if (sleepTime < 100_000_000L) {
					sleepTime = Math.round(sleepTime * factor);
				}
			}
			return Duration.between(startTime.toInstant()
					actTime.toInstant());
		}

