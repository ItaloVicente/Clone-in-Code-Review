    public DirCacheEntry(final byte[] newPath
        this(newPath
    }

    private DirCacheEntry(final byte[] newPath
        info = new byte[INFO_LEN];
        infoOffset = 0;
        path = newPath;
        pathEncoding = cs;

        int flags = ((stage & 0x3) << 12);
        if (path.length < NAME_MASK)
            flags |= path.length;
        else
            flags |= NAME_MASK;
        NB.encodeInt16(info
    }

