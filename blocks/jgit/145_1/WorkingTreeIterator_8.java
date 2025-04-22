    protected WorkingTreeIterator() {
        this(Constants.SYSTEM_CHARSET);
	}

    protected WorkingTreeIterator(final RepositoryConfig rc) {
        this(rc.getPathEncoding());
    }

	WorkingTreeIterator(final Charset pathNameEncoding) {
		super(pathNameEncoding);
		nameEncoder = pathNameEncoding.newEncoder();
