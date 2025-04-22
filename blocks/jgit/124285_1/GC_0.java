		try (DirectoryStream<Path> stream =
				Files.newDirectoryStream(packDir
			stream.forEach(t -> {
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
