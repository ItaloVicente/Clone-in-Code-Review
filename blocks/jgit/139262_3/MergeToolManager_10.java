	private FileElement createBackupFile(String filePath
			throws IOException {
		FileElement backup = new FileElement(filePath
		Files.copy(Paths.get(filePath)
				backup.createTempFile(parentDir).toPath()
				StandardCopyOption.REPLACE_EXISTING);
		return backup;
