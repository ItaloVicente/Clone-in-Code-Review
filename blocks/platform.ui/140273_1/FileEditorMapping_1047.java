	public FileEditorMapping(String extension) {
		this(STAR, extension);
	}

	public FileEditorMapping(String name, String extension) {
		super();
		if (name == null || name.length() < 1) {
			setName(STAR);
		} else {
