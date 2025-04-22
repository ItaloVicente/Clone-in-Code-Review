					guessTool(toolName, gui).getCommand(), localFile,
					remoteFile, mergedFile, null);
			Map<String, String> env = ExternalToolUtils.prepareEnvironment(db,
					localFile,
					remoteFile,
					mergedFile, null);
			boolean trust = config.isTrustExitCode();
			if (trustExitCode.isConfigured()) {
				trust = trustExitCode.toBoolean();
			}
			CommandExecutor cmdExec = new CommandExecutor(db.getFS(), trust);
			return cmdExec.run(command, db.getWorkTree(), env);
		} catch (IOException | InterruptedException e) {
			throw new ToolException(e);
		} finally {
			localFile.cleanTemporaries();
			remoteFile.cleanTemporaries();
			mergedFile.cleanTemporaries();
		}
	}

	/**
	 * @return the tool names
	 */
	public Set<String> getToolNames() {
		return config.getToolNames();
	}

	/**
	 * @return the user defined tools
	 */
