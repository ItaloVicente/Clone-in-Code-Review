
	@Override
	@Nullable
	protected byte[] readIncludedConfig(String relPath)
			throws ConfigInvalidException {
		final File file;
			file = fs.resolve(fs.userHome()
		} else {
			file = fs.resolve(configFile.getParentFile()
		}

		if (!file.exists()) {
			return null;
		}

		try {
			return IO.readFully(file);
		} catch (IOException ioe) {
			throw new ConfigInvalidException(MessageFormat
					.format(JGitText.get().cannotReadFile
		}
	}
