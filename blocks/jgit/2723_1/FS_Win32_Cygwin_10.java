	FS_Win32_Cygwin() {
		super();
	}

	FS_Win32_Cygwin(FS src) {
		super(src);
	}

	public FS newInstance() {
		return new FS_Win32_Cygwin(this);
	}

