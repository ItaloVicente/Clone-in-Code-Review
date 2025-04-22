	@SuppressWarnings("boxing")
	@Test
	public void detectFileModified() throws IOException {
		int failures = 0;
		ArrayList<Long> deltas = new ArrayList<>();
		long racyNanos = 0;
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
		long min = 0;
		long max = 0;
		double avg = 0;
		if (failures > 0) {
			min = deltas.stream().min(Comparator.comparing(Long::longValue))
				.get();
			max = deltas.stream().max(Comparator.comparing(Long::longValue))
				.get();
			avg = deltas.stream().mapToDouble(a -> a).average()
				.getAsDouble();
			System.out.println(String.format(
					"racyNanos=%
					racyNanos
		}
		assertTrue(failures == 0);
	}

