	public void delete(File f) throws IOException {
		if (!f.delete())
			throw new IOException(MessageFormat.format(
					JGitText.get().deleteFileFailed
	}

