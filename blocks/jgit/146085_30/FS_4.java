			LOG.debug("{}: use fallback timestamp resolution for directory {}"
					Thread.currentThread()
			return FALLBACK_FILESTORE_ATTRIBUTES;
		}

		@SuppressWarnings("boxing")
		private static Duration measureMinimalRacyInterval(Path dir) {
			LOG.debug("{}: start measure minimal racy interval in {}"
					Thread.currentThread()
			int failures = 0;
			long racyNanos = 0;
			final int COUNT = 1000;
			ArrayList<Long> deltas = new ArrayList<>();
			try {
				Files.createFile(probe);
				for (int i = 0; i < COUNT; i++) {
					write(probe
					FileSnapshot snapshot = FileSnapshot.save(probe.toFile());
					read(probe);
					write(probe
					if (!snapshot.isModified(probe.toFile())) {
						deltas.add(Long.valueOf(snapshot.lastDelta()));
						racyNanos = snapshot.lastRacyThreshold();
						failures++;
					}
				}
			} catch (IOException e) {
				LOG.error(e.getMessage()
				return FALLBACK_MIN_RACY_INTERVAL;
			} finally {
				deleteProbe(probe);
			}
			if (failures > 0) {
				Stats stats = new Stats();
				for (Long d : deltas) {
					stats.add(d);
				}
				LOG.debug(
								+ "count
								+ "{}
						COUNT
						stats.avg()
				return Duration
						.ofNanos(Double.valueOf(stats.max()).longValue());
			}
			LOG.debug("{}: no failures when measuring minimal racy interval"
					Thread.currentThread());
			return Duration.ZERO;
		}

		private static void write(Path p
			FileUtils.mkdirs(p.getParent().toFile()
			try (Writer w = new OutputStreamWriter(Files.newOutputStream(p)
					UTF_8)) {
				w.write(body);
			}
		}

		private static String read(Path p) throws IOException {
			final byte[] body = IO.readFully(p.toFile());
			return new String(body
