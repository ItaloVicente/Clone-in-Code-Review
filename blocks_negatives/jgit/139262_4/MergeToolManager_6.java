	private FileElement createBackupFile(String mergedFilePath, File tempDir)
			throws IOException {
		FileElement backup = new FileElement(mergedFilePath, "NOID", null); //$NON-NLS-1$
		Files.copy(Paths.get(mergedFilePath),
				backup.getFile(tempDir, "BACKUP").toPath(), //$NON-NLS-1$
				StandardCopyOption.REPLACE_EXISTING);
		return backup;
