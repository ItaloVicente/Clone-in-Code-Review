    public int isPathPrefix(String p) {
        final Charset cs = db.getConfig().getPathEncoding();
        byte[] pRaw = Constants.encode(p
        return isPathPrefix(pRaw
    }

