		final FileInputStream fd = new FileInputStream(idxFile);
		try {
			return read(fd, packIndex, reverseIndex);
		} catch (IOException ioe) {
			throw new IOException(MessageFormat
					.format(JGitText.get().unreadablePackIndex,
							idxFile.getAbsolutePath()),
					ioe);
		} finally {
