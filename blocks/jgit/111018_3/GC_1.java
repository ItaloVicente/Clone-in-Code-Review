	private void deleteTempPacksIdx() {
		Path packDir = Paths.get(repo.getObjectsDirectory().getAbsolutePath()
		Instant threshold = Instant.now().minus(1
		try {
			Files.newDirectoryStream(packDir
					.forEach(t -> {
						try {
							Instant lastModified = Files.getLastModifiedTime(t)
									.toInstant();
							if (lastModified.isBefore(threshold)) {
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

