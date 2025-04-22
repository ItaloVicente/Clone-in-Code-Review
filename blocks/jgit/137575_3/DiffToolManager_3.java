	public int compare(Repository db
			FileElement remoteFile
			String toolName
			BooleanOption gui
			throws DiffToolException {
		int rc = 0;
		ITool tool = guessTool(toolName
		try {
			File workingDir = db.getWorkTree();
			String localFilePath = localFile.getFile(workingDir).getPath();
			String remoteFilePath = remoteFile.getFile(workingDir).getPath();
			String command = tool.getCommand();
			command = command.replace("$LOCAL"
			command = command.replace("$REMOTE"
			command = command.replace("$MERGED"
			Map<String
			env.put(Constants.GIT_DIR_KEY
			env.put("LOCAL"
			env.put("REMOTE"
			env.put("MERGED"
			ExecutionResult result = CommandExecutor.run(db.getFS()
					command
					workingDir
			rc = result.getRc();
			if (rc != 0) {
				boolean trust = tool.isTrustExitCode();
				if (trustExitCode.isDefined()) {
					trust = trustExitCode.toBoolean();
				}
				if (trust) {
					throw new DiffToolException(
							new String(result.getStderr().toByteArray()));
				}
			}
		} catch (Exception e) {
			throw new DiffToolException(e);
		} finally {
			localFile.cleanTemporaries();
			remoteFile.cleanTemporaries();
		}
		return rc;
