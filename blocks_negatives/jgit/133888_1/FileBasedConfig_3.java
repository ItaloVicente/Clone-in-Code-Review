		if (!lf.lock())
			throw new LockFailedException(getFile());
		try {
			lf.setNeedSnapshot(true);
			lf.write(out);
			if (!lf.commit())
				throw new IOException(MessageFormat.format(JGitText.get().cannotCommitWriteTo, getFile()));
		} finally {
			lf.unlock();
