	@SuppressWarnings("boxing")
	public DirCacheEntry(byte[] path
			FileMode fileMode) {
		checkPath(path);
		if (stage < 0 || 3 < stage)
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().invalidStageForPath

		info = new byte[INFO_LEN];
		infoOffset = 0;
		this.path = path;

		int flags = ((stage & 0x3) << 12);
		if (path.length < NAME_MASK)
			flags |= path.length;
		else
			flags |= NAME_MASK;
		NB.encodeInt16(info
		setObjectId(id);
		setFileMode(fileMode);
	}

