	@SuppressWarnings("boxing")
	@Test
	public void detectFileModified() throws IOException {
		int failures = 0;
		long racyNanos = 0;
		Stats stats = new Stats();
		for (int i = 0; i < 10000; i++) {
			File f = createFile("test").toFile();
			write(f
			FileSnapshot snapshot = FileSnapshot.save(f);
			assertEquals("a"
			write(f
			if (!snapshot.isModified(f)) {
				stats.add(snapshot.wasDelta());
				racyNanos = snapshot.wasRacyNanos();
			}
		}
		if (stats.count() > 0) {
			System.out.println(String.format(
					"racyNanos=%
							+ "stddev=%
					racyNanos
					stats.avg()
					stats.avg() - 3 * stats.stddev()
					stats.avg() + 3 * stats.stddev()));
		}
		assertTrue(failures == 0);
	}

