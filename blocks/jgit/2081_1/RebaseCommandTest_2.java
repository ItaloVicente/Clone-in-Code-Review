	private void writeFileAndAdd(String fileName
			throws Exception {
		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			sb.append(line);
			sb.append('\n');
		}
		writeTrashFile(fileName
		git.add().addFilepattern(fileName).call();
	}

	private void checkFile(String fileName
		File file = new File(db.getWorkTree()
		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			sb.append(line);
			sb.append('\n');
		}
		checkFile(file
	}

	public void testStopOnConflictFileCreationAndDeletion() throws Exception {
