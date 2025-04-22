
	@Test
	public void testFsTimestampResolution() throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("uuuu-MMM-dd HH:mm:ss.nnnnnnnnn"
				.withZone(ZoneId.systemDefault());
		Path dir = Files.createTempDirectory("probe-filesystem");
		Duration resolution = FS.getFsTimerResolution(dir);
		long resolutionNs = resolution.toNanos();
		assertTrue(resolutionNs > 0);
		for (int i = 0; i < 10; i++) {
			Path f = null;
			try {
				f = dir.resolve("testTimestampResolution" + i);
				Files.createFile(f);
				FileUtils.touch(f);
				FileTime t1 = Files.getLastModifiedTime(f);
				TimeUnit.NANOSECONDS.sleep(resolutionNs);
				FileUtils.touch(f);
				FileTime t2 = Files.getLastModifiedTime(f);
				assertTrue(String.format(
						"expected t2=%s to be larger than t1=%s\nsince file timestamp resolution was measured to be %
						formatter.format(t2.toInstant())
						formatter.format(t1.toInstant())
						Long.valueOf(resolutionNs))
			} finally {
				Files.delete(f);
			}
		}
	}
