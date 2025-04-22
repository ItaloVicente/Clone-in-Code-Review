	public void writeMergeCommitMsg(String msg) throws IOException {
		File mergeMsgFile = new File(gitDir
		if (msg != null) {
			FileOutputStream fos = new FileOutputStream(mergeMsgFile);
			try {
				fos.write(msg.getBytes(Constants.CHARACTER_ENCODING));
			} finally {
				fos.close();
			}
		} else {
			mergeMsgFile.delete();
		}
	}

