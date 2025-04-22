    public static DirCache read(final File indexLocation
            throws CorruptObjectException
        final DirCache c = new DirCache(indexLocation
        c.read();
        return c;
    }

