	public File getBaseTempDir() throws IOException {
		File userHome = FS.DETECTED.userHome();
		File rootDir = new File(userHome, "EGitCoreTestTempDir");
		return rootDir;
	}

