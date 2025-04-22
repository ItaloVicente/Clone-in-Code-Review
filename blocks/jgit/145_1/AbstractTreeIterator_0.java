    protected AbstractTreeIterator(final RepositoryConfig rc) {
        this(rc.getPathEncoding());
    }

    protected AbstractTreeIterator(final Charset pathNameEncoding) {
        parent = null;
        path = new byte[DEFAULT_PATH_SIZE];
        pathOffset = 0;
        pathEncoding = pathNameEncoding;
    }

