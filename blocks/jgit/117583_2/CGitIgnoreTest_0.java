	private String[] cgitUntracked() throws Exception {
		FS fs = db.getFS();
		ProcessBuilder builder = fs.runInShell("git"
				new String[] { "ls-files"
		builder.directory(db.getWorkTree());
		builder.environment().put("HOME"
		ExecutionResult result = fs.execute(builder
				new ByteArrayInputStream(new byte[0]));
		String errorOut = toString(result.getStderr());
		assertEquals("External git failed"
				"exit " + result.getRc() + '\n' + errorOut);
		try (BufferedReader r = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(result.getStdout().openInputStream())
				Constants.CHARSET))) {
			return r.lines().toArray(String[]::new);
		}
	}

	private void jgitIgnoredAndUntracked(LinkedHashSet<String> ignored
			LinkedHashSet<String> untracked) throws IOException {
