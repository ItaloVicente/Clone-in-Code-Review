	public void renameFile(File origin
		markAsModified(originPath);

		if (inCore) {
			return;
		}
		if (origin == null || dest == null) {
			throw new IOException("Cannot rename file. Either origin or destination files are null");
		}
		try {
			FileUtils.mkdirs(dest.getParentFile()
			FileUtils.rename(origin
		} catch (IOException e) {
			throw new IOException(
					MessageFormat.format(JGitText.get().renameFileFailed
		}
	}

	public void copyFile(File origin
		markAsModified(destPath);

		if (inCore) {
			return;
		}
		if (origin == null || dest == null) {
			throw new IOException("Cannot copy file. Either origin or destination files are null");
		}
		FileUtils.mkdirs(dest.getParentFile()
		Files.copy(origin.toPath()
	}

