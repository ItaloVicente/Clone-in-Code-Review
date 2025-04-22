	FS_Win32() {
		super();
	}

	FS_Win32(FS src) {
		super(src);
	}

	public FS newInstance() {
		return new FS_Win32(this);
	}
