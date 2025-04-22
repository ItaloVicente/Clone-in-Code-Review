		try {
			token = FS.DETECTED.createNewFileAtomic(lck);
		} catch (IOException e) {
			LOG.error(JGitText.get().failedCreateLockFile
			throw e;
		}
