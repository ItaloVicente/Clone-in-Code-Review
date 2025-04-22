			if (backup != null) {
				backup.cleanTemporaries();
			}
			if (!((result == null) && config.isKeepTemporaries())) {
				localFile.cleanTemporaries();
				remoteFile.cleanTemporaries();
				if (baseFile != null) {
					baseFile.cleanTemporaries();
				}
				if (config.isWriteToTemp() && (tempDir != null)
						&& tempDir.exists()) {
					tempDir.delete();
				}
			}
		}
	}

	private static FileElement createBackupFile(String mergedFilePath
			File tempDir) throws IOException {
		FileElement backup = null;
		Path path = Paths.get(tempDir.getPath()
		if (Files.exists(path)) {
			backup = new FileElement(mergedFilePath
			Files.copy(path
					StandardCopyOption.REPLACE_EXISTING);
