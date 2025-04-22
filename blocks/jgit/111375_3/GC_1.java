	private void deleteTempPacksIdx() {
		Path packDir = repo.getObjectDatabase().getPackDirectory().toPath();
		try {
			Files.newDirectoryStream(packDir
					.forEach(t -> {
						try {
							long lastModified = Files.getLastModifiedTime(t)
									.toMillis();
							long threshold = lastModified + 24 * 60 * 60 * 1000;
							if (threshold <= Instant.now().toEpochMilli()) {
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

