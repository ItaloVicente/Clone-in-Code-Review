	protected void copyFile(String srcDir, String targetDir, String fileName) throws IOException {
		Path srcFile = Paths.get(srcDir, fileName);
		if (Files.exists(srcFile)) {
			Path targetFile = Paths.get(targetDir, fileName);
			Files.copy(srcFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
		}
	}
