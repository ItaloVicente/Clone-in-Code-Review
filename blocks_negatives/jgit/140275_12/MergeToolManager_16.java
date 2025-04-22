					tool.getCommand(baseFile != null),
					localFile, remoteFile, mergedFile, baseFile);
			Map<String, String> env = ExternalToolUtils.prepareEnvironment(db, localFile,
					remoteFile, mergedFile, baseFile);
			boolean trust = tool.getTrustExitCode().toBoolean();
			CommandExecutor cmdExec = new CommandExecutor(db.getFS(), trust);
			result = cmdExec.run(command, workingDir, env);
			keepBackupFile(mergedFile.getPath(), backup);
			return result;
		} catch (IOException | InterruptedException e) {
			throw new ToolException(e);
		} finally {
			if (backup != null) {
				backup.cleanTemporaries();
			}
			if (!((result == null) && config.isKeepTemporaries())) {
				localFile.cleanTemporaries();
				remoteFile.cleanTemporaries();
				if (baseFile != null) {
					baseFile.cleanTemporaries();
				}
				if (config.isWriteToTemp() && (tempDir != null)
						&& tempDir.exists()) {
					tempDir.delete();
				}
			}
		}
	}

	private FileElement createBackupFile(String filePath, File parentDir)
			throws IOException {
		FileElement backup = new FileElement(filePath, Type.BACKUP);
		Files.copy(Paths.get(filePath),
				backup.createTempFile(parentDir).toPath(),
				StandardCopyOption.REPLACE_EXISTING);
		return backup;
	}

	/**
	 * @return the created temporary directory if (mergetol.writeToTemp == true)
	 *         or null if not configured or false.
	 * @throws IOException
	 */
	public File createTempDirectory() throws IOException {
		return config.isWriteToTemp()
				: null;
	}

	/**
	 * @return the tool names
	 */
	public Set<String> getToolNames() {
		return config.getToolNames();
	}
