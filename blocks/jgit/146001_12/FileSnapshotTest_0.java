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
			assertEquals("a"
			write(f
			if (!snapshot.isModified(f)) {
				deltas.add(snapshot.wasDelta());
				racyNanos = snapshot.wasRacyNanos();
				failures++;
			}
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
		assertTrue(failures == 0);
	}

