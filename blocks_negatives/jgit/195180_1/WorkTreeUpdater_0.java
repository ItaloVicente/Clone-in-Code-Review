			try (org.eclipse.jgit.util.TemporaryBuffer buffer =
					new org.eclipse.jgit.util.TemporaryBuffer.LocalFile(null)) {
				DirCacheCheckout.getContent(
						repo, path, checkoutMetadata, resultStreamLoader, null, buffer);
				InputStream bufIn = buffer.openInputStream();
				Files.copy(bufIn, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
