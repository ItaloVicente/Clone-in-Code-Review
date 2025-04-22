	private String prepareCommand(String mergedFilePath, String localFilePath,
			String remoteFilePath, String baseFilePath, String command) {
		command = command.replace("$LOCAL", localFilePath); //$NON-NLS-1$
		command = command.replace("$REMOTE", remoteFilePath); //$NON-NLS-1$
		command = command.replace("$MERGED", mergedFilePath); //$NON-NLS-1$
		command = command.replace("$BASE", baseFilePath); //$NON-NLS-1$
		return command;
	}

	private Map<String, String> prepareEnvironment(Repository db,
			String mergedFilePath,
			String localFilePath, String remoteFilePath, String baseFilePath) {
		Map<String, String> env = new TreeMap<>();
		env.put(Constants.GIT_DIR_KEY, db.getDirectory().getAbsolutePath());
		env.put("LOCAL", localFilePath); //$NON-NLS-1$
		env.put("REMOTE", remoteFilePath); //$NON-NLS-1$
		env.put("MERGED", mergedFilePath); //$NON-NLS-1$
		env.put("BASE", baseFilePath); //$NON-NLS-1$
		return env;
	}

