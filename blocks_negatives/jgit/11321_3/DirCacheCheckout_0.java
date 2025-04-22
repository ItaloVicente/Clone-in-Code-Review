		if (!tmpFile.renameTo(f)) {
			FileUtils.delete(f, FileUtils.EMPTY_DIRECTORIES_ONLY
					| FileUtils.RECURSIVE);
			if (!tmpFile.renameTo(f)) {
				throw new IOException(MessageFormat.format(
						JGitText.get().couldNotWriteFile, tmpFile.getPath(),
						f.getPath()));
			}
