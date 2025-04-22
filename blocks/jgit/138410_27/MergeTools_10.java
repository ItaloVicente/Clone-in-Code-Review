	private FileElement createBackupFile(String mergedFilePath
			throws IOException {
		FileElement backup = new FileElement(mergedFilePath
		Files.copy(Paths.get(mergedFilePath)
				backup.getFile(tempDir
				StandardCopyOption.REPLACE_EXISTING);
		return backup;
	}

