
	private void exportPreparedMessage(String msg) throws IOException {
		final File messageFile = getMessageFile(true);
		if (messageFile == null) {
			final String errorMessage = MessageFormat.format(
					JGitText.get().cannotCreateCommitMessageFile
							.getDirectory().getAbsolutePath()
							+ Constants.COMMIT_EDITMSG);
			throw new IOException(errorMessage);
		}
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
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(messageFile));
			StringBuilder builder = new StringBuilder();
			String line = reader.readLine();
			while (line != null) {
				builder.append(line);
				line = reader.readLine();
				if (line != null)
					builder.append('\n');
			}
			return builder.toString();
		} finally {
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
