	FS_POSIX_Java5() {
		super();
	}

	FS_POSIX_Java5(FS src) {
		super(src);
	}

	@Override
	public FS newInstance() {
		return new FS_POSIX_Java5(this);
	}

