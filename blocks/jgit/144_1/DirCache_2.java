        this(indexLocation
	}

    public DirCache(final File indexLocation
        this(indexLocation
    }

    private DirCache(final File indexLocation
        liveFile = indexLocation;
        pathEncoding = pathNameEncoding;
        clear();
    }

    Charset getPathEncoding() {
        return pathEncoding;
    }
