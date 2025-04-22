			try (FileInputStream mergeResultFile = new FileInputStream(
					db.getWorkTree().toPath().resolve(ENABLED_CHECKED_GIF)
							.toFile())) {
				assertFalse(contentEquals(
						getClass().getResourceAsStream(ENABLED_CHECKED_GIF)
						mergeResultFile));
