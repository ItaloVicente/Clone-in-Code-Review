		if (safeWrite) {
			TemporaryBuffer buffer = new TemporaryBuffer.LocalFile(null);
			try {
				try (TemporaryBuffer buf = buffer) {
					DirCacheCheckout.getContent(repo, path, metadata,
							resultStreamLoader, workingTreeOptions, buf);
				}
				try (InputStream bufIn = buffer.openInputStream()) {
					Files.copy(bufIn, file.toPath(),
							StandardCopyOption.REPLACE_EXISTING);
				}
			} finally {
				buffer.destroy();
			}
			return;
		}
