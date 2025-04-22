	private final boolean safe;

	private boolean lfOnlyFound;

	private boolean crlfFound;

	public EolCanonicalizingInputStream(InputStream in
		this.in = in;
		this.safe = safe;
	}

