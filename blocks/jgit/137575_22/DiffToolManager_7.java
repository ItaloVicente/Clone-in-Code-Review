	public ExecutionResult compare(Repository db
			FileElement remoteFile
			String toolName
			BooleanOption gui
			throws ToolException {
		ExternalDiffTool tool = guessTool(toolName
		try {
			File workingDir = db.getWorkTree();
			String localFilePath = localFile.getFile().getPath();
			String remoteFilePath = remoteFile.getFile().getPath();
			String command = tool.getCommand();
			command = command.replace("$LOCAL"
			command = command.replace("$REMOTE"
			command = command.replace("$MERGED"
			Map<String
			env.put(Constants.GIT_DIR_KEY
			env.put("LOCAL"
			env.put("REMOTE"
			env.put("MERGED"
			boolean trust = config.isTrustExitCode();
			if (trustExitCode.isConfigured()) {
				trust = trustExitCode.toBoolean();
			}
			CommandExecutor cmdExec = new CommandExecutor(db.getFS()
			return cmdExec.run(command
		} catch (IOException | InterruptedException e) {
			throw new ToolException(e);
		} finally {
			localFile.cleanTemporaries();
			remoteFile.cleanTemporaries();
		}
