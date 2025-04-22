	public String readSquashCommitMsg() throws IOException {
		return readCommitMsgFile(Constants.SQUASH_MSG);
	}

	public void writeSquashCommitMsg(String msg) throws IOException {
		File squashMsgFile = new File(gitDir
		writeCommitMsg(squashMsgFile
	}

	private String readCommitMsgFile(String msgFilename) throws IOException {
		if (isBare() || getDirectory() == null)
			throw new NoWorkTreeException();

		File mergeMsgFile = new File(getDirectory()
		try {
			return RawParseUtils.decode(IO.readFully(mergeMsgFile));
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	private void writeCommitMsg(File msgFile
		if (msg != null) {
			FileOutputStream fos = new FileOutputStream(msgFile);
			try {
				fos.write(msg.getBytes(Constants.CHARACTER_ENCODING));
			} finally {
				fos.close();
			}
		} else {
			FileUtils.delete(msgFile
		}
	}

