		@SuppressWarnings("boxing")
		private static Duration measureMinimalRacyInterval(Path dir) {
			LOG.debug("{}: start measure minimal racy interval in {}"
					Thread.currentThread()
			int n = 0;
			int failures = 0;
			long racyNanos = 0;
			ArrayList<Long> deltas = new ArrayList<>();
			Instant end = Instant.now().plusSeconds(3);
			try {
				Files.createFile(probe);
				do {
					n++;
					write(probe
					FileSnapshot snapshot = FileSnapshot.save(probe.toFile());
					read(probe);
					write(probe
					if (!snapshot.isModified(probe.toFile())) {
						deltas.add(Long.valueOf(snapshot.lastDelta()));
						racyNanos = snapshot.lastRacyThreshold();
						failures++;
					}
				} while (Instant.now().compareTo(end) < 0);
			} catch (IOException e) {
				LOG.error(e.getMessage()
				return FALLBACK_MIN_RACY_INTERVAL;
			} finally {
				deleteProbe(probe);
