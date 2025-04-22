	private void deleteTempPacksIdx() {
		Path packDir = repo.getObjectDatabase().getPackDirectory().toPath();
		try {
			Files.newDirectoryStream(packDir
					.forEach(t -> {
						try {
							Files.deleteIfExists(t);
						} catch (IOException e) {
							LOG.error(e.getMessage()
						}
					});
		} catch (IOException e) {
			LOG.error(e.getMessage()
		}
	}

