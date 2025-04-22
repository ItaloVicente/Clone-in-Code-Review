		ExternalMergeTool tool = guessTool(toolName
		try {
			File workingDir = repo.getWorkTree();
			String localFilePath = localFile.getFile().getPath();
			String remoteFilePath = remoteFile.getFile().getPath();
			String baseFilePath = baseFile.getFile().getPath();
			String command = tool.getCommand();
			command = command.replace("$LOCAL"
			command = command.replace("$REMOTE"
			command = command.replace("$MERGED"
			command = command.replace("$BASE"
			Map<String
			env.put(Constants.GIT_DIR_KEY
					repo.getDirectory().getAbsolutePath());
			env.put("LOCAL"
			env.put("REMOTE"
			env.put("MERGED"
			env.put("BASE"
			boolean trust = tool.getTrustExitCode() == BooleanTriState.TRUE;
			CommandExecutor cmdExec = new CommandExecutor(repo.getFS()
			return cmdExec.run(command
		} catch (Exception e) {
			throw new ToolException(e);
		} finally {
			localFile.cleanTemporaries();
			remoteFile.cleanTemporaries();
			baseFile.cleanTemporaries();
		}
