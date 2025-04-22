		}
		File hookFile = new File(hookDir
		if (hookFile.isAbsolute()) {
			if (!hookFile.exists() || FS.DETECTED.supportsExecute()
					&& !FS.DETECTED.canExecute(hookFile)) {
				return null;
			}
		} else {
			try {
				File runDirectory = getRunDirectory(repository
				if (runDirectory == null) {
					return null;
				}
				Path hookPath = runDirectory.getAbsoluteFile().toPath()
						.resolve(hookFile.toPath());
				FS fs = repository.getFS();
				if (fs == null) {
					fs = FS.DETECTED;
				}
				if (!Files.exists(hookPath) || fs.supportsExecute()
						&& !fs.canExecute(hookPath.toFile())) {
					return null;
				}
				hookFile = hookPath.toFile();
			} catch (InvalidPathException e) {
				LOG.warn(MessageFormat.format(JGitText.get().invalidHooksPath
						hookFile));
				return null;
			}
		}
		return hookFile;
	}

	private File getRunDirectory(Repository repository
			@NonNull String hookName) {
		if (repository.isBare()) {
			return repository.getDirectory();
		}
		switch (hookName) {
			return repository.getDirectory();
		default:
			return repository.getWorkTree();
		}
	}

	private File getHooksDirectory(Repository repository) {
		Config config = repository.getConfig();
		String hooksDir = config.getString(ConfigConstants.CONFIG_CORE_SECTION
				null
		if (hooksDir != null) {
			return new File(hooksDir);
		}
		File dir = repository.getDirectory();
		return dir == null ? null : new File(dir
