	@Override
	public void setShallowCommits(Set<ObjectId> shallowCommits) throws IOException {
		this.shallowCommitsIds = shallowCommits;
		LockFile lock = new LockFile(shallowFile);
		if (!lock.lock()) {
			throw new IOException(MessageFormat.format(JGitText.get().lockError
					shallowFile.getAbsolutePath()));
		}

		try {
			if (shallowCommits.isEmpty()) {
				if (shallowFile.isFile()) {
					shallowFile.delete();
				}
			} else {
				try (OutputStream out = lock.getOutputStream()) {
					for (ObjectId shallowCommit : shallowCommits) {
						byte[] buf = new byte[Constants.OBJECT_ID_STRING_LENGTH + 1];
						shallowCommit.copyTo(buf
						buf[Constants.OBJECT_ID_STRING_LENGTH] = '\n';
						out.write(buf);
					}
				} finally {
					lock.commit();
				}
			}
		} finally {
			lock.unlock();
		}

		if (shallowCommits.isEmpty()) {
			shallowFileSnapshot = FileSnapshot.DIRTY;
		} else {
			shallowFileSnapshot = FileSnapshot.save(shallowFile);
		}
	}

