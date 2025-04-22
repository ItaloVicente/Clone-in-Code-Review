		}
		try {
			FileUtils.rename(tmpFile, f);
		} catch (IOException e) {
			throw new IOException(MessageFormat.format(
					JGitText.get().couldNotWriteFile, tmpFile.getPath(),
					f.getPath()));
