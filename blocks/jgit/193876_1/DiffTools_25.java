	public ExecutionResult compare(FileElement localFile
			FileElement remoteFile
			BooleanTriState prompt
			BooleanTriState trustExitCode) throws ToolException {
		try {
			String command = ExternalToolUtils.prepareCommand(
					guessTool(toolName
					remoteFile
			Map<String
					localFile
			boolean trust = config.isTrustExitCode();
			if (trustExitCode != BooleanTriState.UNSET) {
				trust = trustExitCode == BooleanTriState.TRUE;
			}
			CommandExecutor cmdExec = new CommandExecutor(repo.getFS()
			return cmdExec.run(command
		} catch (IOException | InterruptedException e) {
			throw new ToolException(e);
		} finally {
			localFile.cleanTemporaries();
			remoteFile.cleanTemporaries();
			mergedFile.cleanTemporaries();
		}
