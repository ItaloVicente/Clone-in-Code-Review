				long wait = 512;
				long start = System.nanoTime();
				FileTime t1 = Files.getLastModifiedTime(probe);
				FileTime t2 = t1;
				while (t2.compareTo(t1) <= 0) {
					TimeUnit.NANOSECONDS.sleep(wait);
					checkTimeout(s, start);
					FileUtils.touch(probe);
					t2 = Files.getLastModifiedTime(probe);
					if (wait < 100_000_000L) {
						wait = wait * 2;
