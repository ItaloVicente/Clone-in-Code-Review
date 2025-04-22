	public String readMergeCommitMsg() throws IOException {
		File mergeMsgFile = new File(gitDir, Constants.MERGE_MSG);
		try {
			return new String(IO.readFully(mergeMsgFile));
		} catch (FileNotFoundException e) {
			return null;
		}
	}
