	@Test
	public void fileSnapshotEquals() throws Exception {
		FileSnapshot fs1 = FileSnapshot.MISSING_FILE;
		FileSnapshot fs2 = FileSnapshot.save(fs1.lastModifiedInstant());

		assertTrue(fs1.equals(fs2));
		assertTrue(fs2.equals(fs1));
	}

	@SuppressWarnings("boxing")
	@Test
	public void detectFileModified() throws IOException {
		int failures = 0;
		long racyNanos = 0;
		final int COUNT = 10000;
		ArrayList<Long> deltas = new ArrayList<>();
		File f = createFile("test").toFile();
		for (int i = 0; i < COUNT; i++) {
			write(f
			FileSnapshot snapshot = FileSnapshot.save(f);
			assertEquals("file should contain 'a'"
			write(f
			if (!snapshot.isModified(f)) {
				deltas.add(snapshot.lastDelta());
				racyNanos = snapshot.lastRacyThreshold();
				failures++;
			}
			assertEquals("file should contain 'b'"
		}
		if (failures > 0) {
			Stats stats = new Stats();
			LOG.debug(
					"delta [ns] since modification FileSnapshot failed to detect");
			for (Long d : deltas) {
				stats.add(d);
				LOG.debug(String.format("%
			}
			LOG.error(
					"count
			LOG.error(String.format(
					"%
					failures
					stats.avg()
		}
		assertTrue(
				String.format(
						"FileSnapshot: failures to detect file modifications"
								+ " %d out of %d\n"
								+ "timestamp resolution %d µs"
								+ " min racy threshold %d µs"
						
						fsAttrCache.getFsTimestampResolution().toNanos() / 1000
						fsAttrCache.getMinimalRacyInterval().toNanos() / 1000)
				failures == 0);
	}

	private Path createFile(String string) throws IOException {
		Files.createDirectories(trash);
		return Files.createTempFile(trash
