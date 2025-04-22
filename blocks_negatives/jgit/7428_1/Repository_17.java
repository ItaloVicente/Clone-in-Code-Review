	public void writeSquashCommitMsg(String msg) throws IOException {
		File squashMsgFile = new File(gitDir, Constants.SQUASH_MSG);
		writeCommitMsg(squashMsgFile, msg);
	}

	private String readCommitMsgFile(String msgFilename) throws IOException {
		if (isBare() || getDirectory() == null)
			throw new NoWorkTreeException();

		File mergeMsgFile = new File(getDirectory(), msgFilename);
		try {
			return RawParseUtils.decode(IO.readFully(mergeMsgFile));
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	private void writeCommitMsg(File msgFile, String msg) throws IOException {
		if (msg != null) {
			FileOutputStream fos = new FileOutputStream(msgFile);
			try {
				fos.write(msg.getBytes(Constants.CHARACTER_ENCODING));
			} finally {
				fos.close();
			}
		} else {
			FileUtils.delete(msgFile, FileUtils.SKIP_MISSING);
		}
	}

	/**
	 * Read a file from the git directory.
	 *
	 * @param filename
	 * @return the raw contents or null if the file doesn't exist or is empty
	 * @throws IOException
	 */
	private byte[] readGitDirectoryFile(String filename) throws IOException {
		File file = new File(getDirectory(), filename);
		try {
			byte[] raw = IO.readFully(file);
			return raw.length > 0 ? raw : null;
		} catch (FileNotFoundException notFound) {
			return null;
		}
	}
