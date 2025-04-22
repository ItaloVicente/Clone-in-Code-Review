	private void createSubDirectory(String parentName, String newDirName)
			throws IOException {
		String newDirPath = parentName + File.separatorChar + newDirName;
		File newDir = new File(newDirPath);
		newDir.mkdir();
		for (int i = 0; i < directoryNames.length; i++) {
			createFile(newDirPath, fileNames[i]);
		}
	}
