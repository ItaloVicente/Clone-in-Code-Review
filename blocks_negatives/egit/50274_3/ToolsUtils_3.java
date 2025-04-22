		if (tempDir != null && reusedTempDir == null) {
			try {
				deleteTempDirectory(tempDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
