
	@Override
	public boolean supportsSymlinks() {
		return true;
	}

	@Override
	public void createSymLink(File path
		ProcessBuilder processBuilder = runInShell(
				"ln"
		processBuilder.directory(path.getParentFile());
		Process process = processBuilder.start();
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}

	@Override
	public boolean isSymLink(File path) throws IOException {
		ProcessBuilder processBuilder = runInShell(
				"test"
		Process process = processBuilder.start();
		int exitValue = -1;
		try {
			exitValue = process.waitFor();
		} catch (InterruptedException e) {
			throw new JGitInternalException(e.getMessage()
		}
		return exitValue == 0;
	}

	@Override
	public String readSymLink(File path) throws IOException {
		ProcessBuilder processBuilder = runInShell(
				"readlink"
		Process process = processBuilder.start();
		String readLine = new BufferedReader(new InputStreamReader(
				process.getInputStream())).readLine();
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			throw new JGitInternalException(e.getMessage()
		}
		return readLine;
	}

	@Override
	public long length(File path) throws Exception {
		if (isSymLink(path)) {
			return readSymLink(path).toString().getBytes(
					Constants.CHARACTER_ENCODING).length;
		} else {
			return super.length(path);
		}
	}

