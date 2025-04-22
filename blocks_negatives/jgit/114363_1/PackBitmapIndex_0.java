			final String path = idxFile.getAbsolutePath();
			final IOException err;
			err = new IOException(MessageFormat.format(
					JGitText.get().unreadablePackIndex, path));
			err.initCause(ioe);
			throw err;
