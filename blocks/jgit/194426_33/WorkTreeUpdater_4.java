	public void copyFile(File origin
		markAsModified(destPath);

		if (inCore) {
			return;
		}
		if (origin == null || dest == null) {
			throw new IOException(JGitText.get().copyFileFailedNullFiles);
		}
		FileUtils.mkdirs(dest.getParentFile()
		Files.copy(origin.toPath()
	}

