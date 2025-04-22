	@SuppressWarnings("boxing")
	@Test
	public void detectFileModified() throws IOException {
		int failures = 0;
		long racyNanos = 0;
		ArrayList<Long> deltas = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			File f = createFile("test").toFile();
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
			for (Long d : deltas) {
				stats.add(d);
			}
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

