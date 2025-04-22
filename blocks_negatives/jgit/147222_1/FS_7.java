				FileTime startTime = Files.getLastModifiedTime(probe);
				FileTime actTime = startTime;
				long sleepTime = 512;
				while (actTime.compareTo(startTime) <= 0) {
					TimeUnit.NANOSECONDS.sleep(sleepTime);
					FileUtils.touch(probe);
					actTime = Files.getLastModifiedTime(probe);
					if (sleepTime < 100_000_000L) {
						sleepTime = sleepTime * 2;
					}
