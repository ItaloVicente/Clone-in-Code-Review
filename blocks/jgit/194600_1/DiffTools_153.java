	public ExecutionResult compare(FileElement localFile
			FileElement remoteFile
			boolean trustExitCode) throws ToolException {
		try {
			if (tool == null) {
				throw new ToolException(JGitText
						.get().diffToolNotSpecifiedInGitAttributesError);
			}
			String command = ExternalToolUtils.prepareCommand(tool.getCommand()
					localFile
			Map<String
					gitDir
			CommandExecutor cmdExec = new CommandExecutor(fs
			return cmdExec.run(command
		} catch (IOException | InterruptedException e) {
			throw new ToolException(e);
		} finally {
			localFile.cleanTemporaries();
			remoteFile.cleanTemporaries();
		}
