	private static final class FileStoreAttributeCache {
		private static final Duration FALLBACK_TIMESTAMP_RESOLUTION = Duration
				.ofMillis(2000);

		private static final Map<FileStore

		static Duration getFsTimestampResolution(Path file) {
			try {
				FileStore s = Files.getFileStore(file);
				FileStoreAttributeCache c = attributeCache.get(s);
				if (c == null) {
					c = new FileStoreAttributeCache(file);
					attributeCache.put(s
					if (LOG.isDebugEnabled()) {
						LOG.debug(c.toString());
					}
				}
				return c.getFsTimestampResolution();

			} catch (IOException | InterruptedException e) {
				LOG.warn(e.getMessage()
				return FALLBACK_TIMESTAMP_RESOLUTION;
			}
		}

		private Duration fsTimestampResolution;

		Duration getFsTimestampResolution() {
			return fsTimestampResolution;
		}

		FileStoreAttributeCache(Path file)
				throws IOException
			if (!Files.exists(file)) {
				throw new FileNotFoundException(file.toString());
			}
			Path dir = Files.isDirectory(file) ? file : file.getParent();
			Files.createFile(probe);
			try {
				FileTime startTime = Files.getLastModifiedTime(probe);
				FileTime actTime = startTime;
				long sleepTime = 512;
				while (actTime.compareTo(startTime) <= 0) {
					TimeUnit.NANOSECONDS.sleep(sleepTime);
					FileUtils.touch(probe);
					actTime = Files.getLastModifiedTime(probe);
					if (sleepTime < 100_000_000L) {
						sleepTime = sleepTime * 2;
					}
				}
				fsTimestampResolution = Duration.between(startTime.toInstant()
						actTime.toInstant());
			} finally {
				Files.delete(probe);
			}
		}

		@SuppressWarnings("nls")
		@Override
		public String toString() {
			return "FileStoreAttributeCache[" + attributeCache.keySet()
					.stream()
					.map(key -> "FileStore[" + key + "]: fsTimestampResolution="
							+ attributeCache.get(key).getFsTimestampResolution())
					.collect(Collectors.joining("
		}
	}

