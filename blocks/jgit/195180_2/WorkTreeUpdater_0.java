			TemporaryBuffer buffer = new TemporaryBuffer.LocalFile(null);
			try {
				try (TemporaryBuffer buf = buffer) {
					DirCacheCheckout.getContent(repo
							resultStreamLoader
				}
				try (InputStream bufIn = buffer.openInputStream()) {
					Files.copy(bufIn
							StandardCopyOption.REPLACE_EXISTING);
				}
			} finally {
				buffer.destroy();
