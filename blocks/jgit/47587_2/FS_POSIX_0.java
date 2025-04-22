	protected FS_POSIX(FS src) {
		super(src);
		if (src instanceof FS_POSIX) {
			umask = ((FS_POSIX) src).umask;
		}
	}
