	public String readSymLink(File path) throws IOException {
		throw new SymlinksNotSupportedException(
				JGitText.get().errorSymlinksNotSupported);
	}

	public boolean isSymLink(File path) throws IOException {
		return false;
	}

	public boolean exists(File path) {
		return path.exists();
	}

	public boolean isDirectory(File path) {
		return path.isDirectory();
	}

	public boolean isFile(File path) {
		return path.isFile();
	}

	public boolean isHidden(File path) throws IOException {
		return path.isHidden();
	}

	public void setHidden(File path
			throw new IllegalArgumentException(
					"Hiding only allowed for names that start with a period");
	}

	public void createSymLink(File path
		throw new SymlinksNotSupportedException(
				JGitText.get().errorSymlinksNotSupported);
	}

