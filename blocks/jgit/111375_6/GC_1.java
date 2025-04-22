	private void deleteTempPacksIdx() {
		Path packDir = repo.getObjectDatabase().getPackDirectory().toPath();
		long threshold = Instant.now().toEpochMilli() - 24 * 60 * 60 * 1000;
		try {
			Files.newDirectoryStream(packDir
					.forEach(t -> {
						try {
							long lastModified = Files.getLastModifiedTime(t)
									.toMillis();
							if (lastModified < threshold) {
								Files.deleteIfExists(t);
							}
						} catch (IOException e) {
							LOG.error(e.getMessage()
						}
					});
		} catch (IOException e) {
			LOG.error(e.getMessage()
		}
	}

