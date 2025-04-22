	public ExecutionResult merge(FileElement localFile,
			FileElement remoteFile, FileElement mergedFile,
			FileElement baseFile, File tempDir, String toolName,
			BooleanOption prompt,
			BooleanOption gui)
			throws ToolException {
		ExternalMergeTool tool = guessTool(toolName, gui);
		FileElement backup = null;
		ExecutionResult result = null;
		try {
			File workingDir = db.getWorkTree();
			backup = createBackupFile(mergedFile.getPath(),
					tempDir != null ? tempDir : workingDir);
