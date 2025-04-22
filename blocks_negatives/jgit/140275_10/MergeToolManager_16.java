	/**
	 * @param db the repository database
	 */
	public MergeToolManager(Repository db) {
		this.db = db;
		config = db.getConfig().get(MergeToolConfig.KEY);
		predefinedTools = setupPredefinedTools();
		userDefinedTools = setupUserDefinedTools(config, predefinedTools);
	}

	/**
	 * @param localFile
	 *            the local file element
	 * @param remoteFile
	 *            the remote file element
	 * @param mergedFile
	 *            the merged file element
	 * @param baseFile
	 *            the base file element (can be null)
	 * @param tempDir
	 *            the temporary directory (needed for backup and auto-remove,
	 *            can be null)
	 * @param toolName
	 *            the selected tool name (can be null)
	 * @param prompt
	 *            the prompt option
	 * @param gui
	 *            the GUI option
	 * @return the execution result from tool
	 * @throws ToolException
	 */
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
