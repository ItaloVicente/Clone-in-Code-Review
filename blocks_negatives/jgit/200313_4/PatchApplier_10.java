			if (f != null) {
				TemporaryBuffer buffer = new TemporaryBuffer.LocalFile(null);
				try {
					CheckoutMetadata metadata = new CheckoutMetadata(streamType,
							smudgeFilterCommand);

					try (TemporaryBuffer buf = buffer) {
						DirCacheCheckout.getContent(repo, pathWithOriginalContent,
								metadata, resultStreamLoader.supplier, workingTreeOptions,
								buf);
					}
					try (InputStream bufIn = buffer.openInputStream()) {
						Files.copy(bufIn, f.toPath(),
								StandardCopyOption.REPLACE_EXISTING);
					}
				} finally {
					buffer.destroy();
				}
