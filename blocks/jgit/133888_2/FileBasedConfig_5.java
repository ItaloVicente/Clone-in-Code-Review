		synchronized (this) {
			lf.lockAndWait(MAX_RETRIES
			try {
				lf.setNeedSnapshot(true);
				lf.write(out);
				if (!lf.commit()) {
					throw new IOException(MessageFormat.format(
							JGitText.get().cannotCommitWriteTo
				}
			} finally {
				lf.unlock();
			}
