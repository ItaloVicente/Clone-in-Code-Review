
	@Test
	public void testGetFsTimestampResolution() throws IOException {
		Path dir = Files.createTempDirectory("probe-filesystem");
		Duration resolution = FS.getFsTimerResolution(dir);
		assertTrue(resolution.toNanos() > 0);
		System.out.println(String.format(
				"filesystem timer resolution in directory %s is %
				dir
	}
