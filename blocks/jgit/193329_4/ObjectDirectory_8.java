	@Override
	public void setShallowCommits(Set<ObjectId> shallowCommits) throws IOException {
		this.shallowCommitsIds = shallowCommits;
		LockFile lock = new LockFile(shallowFile);
		if (!lock.lock()) {
			throw new IOException(MessageFormat.format(JGitText.get().lockError
					shallowFile.getAbsolutePath()));
		}
		try {
			for (ObjectId shallowCommit : shallowCommits) {
				lock.write(shallowCommit);
			}
			lock.commit();
		} finally {
			lock.unlock();
		}

		shallowFileSnapshot = FileSnapshot.save(shallowFile);
	}

