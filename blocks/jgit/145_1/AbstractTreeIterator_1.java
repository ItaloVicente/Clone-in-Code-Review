    protected AbstractTreeIterator(final String prefix
        this(prefix
    }

    AbstractTreeIterator(final String prefix
        parent = null;

        if (prefix != null && prefix.length() > 0) {
            final ByteBuffer b;

            b = pathNameEncoding.encode(CharBuffer.wrap(prefix));
            pathLen = b.limit();
            path = new byte[Math.max(DEFAULT_PATH_SIZE
            b.get(path
            if (path[pathLen - 1] != '/')
                path[pathLen++] = '/';
            pathOffset = pathLen;
