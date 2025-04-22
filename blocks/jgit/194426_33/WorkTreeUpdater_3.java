			addCheckoutMetadata(cleanupMetadataByPath
		}
	}

	public void renameFile(File origin
		markAsModified(originPath);
		markAsModified(destPath);

		if (inCore) {
			return;
		}
		if (origin == null || dest == null) {
			throw new IOException(JGitText.get().renameFileFailedNullFiles);
		}
		try {
			FileUtils.mkdirs(dest.getParentFile()
			FileUtils.rename(origin
		} catch (IOException e) {
			throw new IOException(
					MessageFormat.format(JGitText.get().renameFileFailed
