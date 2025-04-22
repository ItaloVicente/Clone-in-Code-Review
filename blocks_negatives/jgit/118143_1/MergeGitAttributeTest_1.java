			mergeResultFile = new FileInputStream(db.getWorkTree().toPath()
					.resolve(ENABLED_CHECKED_GIF).toFile());
			assertTrue(contentEquals(
					getClass().getResourceAsStream(ENABLED_CHECKED_GIF),
					mergeResultFile));
		} finally {
			if (mergeResultFile != null) {
				mergeResultFile.close();
