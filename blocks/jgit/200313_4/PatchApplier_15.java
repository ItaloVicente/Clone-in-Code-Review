				try (TemporaryBuffer buf = buffer) {
					DirCacheCheckout.getContent(repo
							metadata
							buf);
				}
				try (InputStream bufIn = buffer.openInputStream()) {
					Files.copy(bufIn
							StandardCopyOption.REPLACE_EXISTING);
				}
			} finally {
				buffer.destroy();
