		private static Duration singleMeasureTimestampResolution(FileStore s
				Path probe
				throws IOException
			long start = System.nanoTime();
			FileUtils.touch(probe);
			FileTime t1 = Files.getLastModifiedTime(probe);
			FileTime t2 = t1;
			long waitNanos = wait.toNanos();
			while (t2.compareTo(t1) <= 0) {
				TimeUnit.NANOSECONDS.sleep(waitNanos);
				checkTimeout(s
				FileUtils.touch(probe);
				t2 = Files.getLastModifiedTime(probe);
				if (waitNanos < 100_000_000L) {
					waitNanos = Math.round(waitNanos * factor);
				}
			}
			Duration resolution = Duration.between(t1.toInstant()
			return resolution;
		}

