		try {
			Files.newDirectoryStream(packDir, "gc_*_tmp") //$NON-NLS-1$
					.forEach(t -> {
						try {
							Instant lastModified = Files.getLastModifiedTime(t)
									.toInstant();
							if (lastModified.isBefore(threshold)) {
								Files.deleteIfExists(t);
							}
						} catch (IOException e) {
							LOG.error(e.getMessage(), e);
						}
					});
