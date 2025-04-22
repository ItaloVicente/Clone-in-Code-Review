	private String prepareCommand(String mergedFilePath
			String remoteFilePath
		command = command.replace("$LOCAL"
		command = command.replace("$REMOTE"
		command = command.replace("$MERGED"
		command = command.replace("$BASE"
		return command;
	}

	private Map<String
			String mergedFilePath
			String localFilePath
		Map<String
		env.put(Constants.GIT_DIR_KEY
		env.put("LOCAL"
		env.put("REMOTE"
		env.put("MERGED"
		env.put("BASE"
		return env;
	}

	private void keepBackupFile(String mergedFilePath
			throws IOException {
		if (config.isKeepBackup()) {
			Path backupPath = backup.getFile().toPath();
			Files.move(backupPath
					backupPath.resolveSibling(
							Paths.get(mergedFilePath).getFileName() + ".orig")
					StandardCopyOption.REPLACE_EXISTING);
		}
	}

