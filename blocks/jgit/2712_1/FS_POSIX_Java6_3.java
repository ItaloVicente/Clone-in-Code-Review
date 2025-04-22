	FS_POSIX_Java6() {
		super();
	}

	FS_POSIX_Java6(FS src) {
		super(src);
	}

	@Override
	public FS newInstance() {
		return new FS_POSIX_Java6(this);
	}

