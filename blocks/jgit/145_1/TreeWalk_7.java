    public boolean isPathSuffix(final String p) {
        final Charset cs = db.getConfig().getPathEncoding();
        byte[] pRaw = Constants.encode(p
        return isPathSuffix(pRaw
	}

