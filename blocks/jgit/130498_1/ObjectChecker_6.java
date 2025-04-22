	private boolean isMacHFSGit(byte[] raw
			@Nullable AnyObjectId id) throws CorruptObjectException {
		byte[] git = new byte[] { '.'
		return isMacHFSPath(raw
	}

	private boolean isMacHFSGitmodules(byte[] raw
			@Nullable AnyObjectId id) throws CorruptObjectException {
		return isMacHFSPath(raw
	}

