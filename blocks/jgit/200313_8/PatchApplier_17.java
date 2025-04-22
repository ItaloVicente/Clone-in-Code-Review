				try (InputStream bufIn = buffer.openInputStream()) {
					Files.copy(bufIn
							StandardCopyOption.REPLACE_EXISTING);
				}
			} finally {
				buffer.destroy();
