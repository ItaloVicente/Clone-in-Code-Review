		private static void checkTimeout(FileStore s
				throws TimeoutException {
			if (System.nanoTime() - start >= FALLBACK_TIMESTAMP_RESOLUTION
					.toNanos()) {
				throw new TimeoutException(MessageFormat.format(JGitText
						.get().timeoutMeasureFsTimestampResolution
						s.toString()));
			}
		}
		private static void deleteProbe(Path probe) {
			if (Files.exists(probe)) {
				try {
					Files.delete(probe);
				} catch (IOException e) {
					LOG.error(e.getLocalizedMessage()
				}
			}
		}

		private Duration fsTimestampResolution;

		Duration getFsTimestampResolution() {
			return fsTimestampResolution;
		}

		private FileStoreAttributeCache(Duration fsTimestampResolution) {
			this.fsTimestampResolution = fsTimestampResolution;
