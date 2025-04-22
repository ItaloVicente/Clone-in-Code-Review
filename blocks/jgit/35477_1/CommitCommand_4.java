	public CommitCommand setNoVerify(boolean noVerify) {
		this.noVerify = noVerify;
		return this;
	}

	private void exportPreparedMessage(String msg) throws IOException {
		final File messageFile = getMessageFile(true);
		if (messageFile == null)
			throw new RuntimeException("Couldn't create message file "
					+ repo.getDirectory().getAbsolutePath()
					+ Constants.COMMIT_EDITMSG + '.');
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(messageFile));
			writer.write(msg);
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	private String readPreparedMessage() throws IOException {
		final File messageFile = getMessageFile(false);
		if (messageFile == null)
			return "";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(messageFile));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line).append('\n');
			}
			return builder.toString();
		}finally {
			if (reader != null)
				reader.close();
		}
	}

	private File getMessageFile(boolean createOnDemand) throws IOException {
		final File gitdir = repo.getDirectory();
		final File[] messageFileCandidates = gitdir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isFile()
						&& pathname.getName().equals(Constants.COMMIT_EDITMSG);
			}
		});
		final File messageFile;
		if (messageFileCandidates.length > 0)
			messageFile = messageFileCandidates[0];
		else if (createOnDemand) {
			messageFile = new File(gitdir.getAbsolutePath()
					Constants.COMMIT_EDITMSG);
			messageFile.createNewFile();
		} else
			messageFile = null;
		return messageFile;
	}

	private void commitRejectedByHook(Hook cause
			throws RejectedCommitException {
		final String defaultMessage = "Commit rejected by " + cause.getName()
				+ " hook";
		final String errorMessage;
		if (errorDetails.length() > 0)
			errorMessage = defaultMessage + ":\n" + errorDetails;
		else
			errorMessage = defaultMessage + '.';
		throw new RejectedCommitException(errorMessage);
	}
