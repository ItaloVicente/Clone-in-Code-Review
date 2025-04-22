		try {
			FileUtils.rename(tmpFile
		} catch (IOException e) {
			throw new IOException(MessageFormat.format(
					JGitText.get().couldNotWriteFile
					f.getPath()));
