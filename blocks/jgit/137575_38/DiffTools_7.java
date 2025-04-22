	public ExecutionResult compare(Repository repo
			FileElement remoteFile
			Optional<Boolean> prompt
			Optional<Boolean> trustExitCode) throws ToolException {
		ExternalDiffTool tool = guessTool(toolName
		try {
			File workingDir = repo.getWorkTree();
			String localFilePath = localFile.getFile().getPath();
			String remoteFilePath = remoteFile.getFile().getPath();
			String command = tool.getCommand();
			command = command.replace("$LOCAL"
			command = command.replace("$REMOTE"
			command = command.replace("$MERGED"
			Map<String
			env.put(Constants.GIT_DIR_KEY
					repo.getDirectory().getAbsolutePath());
			env.put("LOCAL"
			env.put("REMOTE"
			env.put("MERGED"
			boolean trust = config.isTrustExitCode();
			if (trustExitCode.isPresent()) {
				trust = trustExitCode.get().booleanValue();
			}
			CommandExecutor cmdExec = new CommandExecutor(repo.getFS()
			return cmdExec.run(command
		} catch (IOException | InterruptedException e) {
			throw new ToolException(e);
		} finally {
			localFile.cleanTemporaries();
			remoteFile.cleanTemporaries();
		}
