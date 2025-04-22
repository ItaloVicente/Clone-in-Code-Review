    public void reset(final byte[] treeData
        Charset pathNameEncoding = null;
        if (rc != null) {
            pathNameEncoding = rc.getPathEncoding();
        }
        reset(treeData
    }

    void reset(final byte[] treeData
        raw = treeData;
        prevPtr = -1;
        currPtr = 0;
        if (pathNameEncoding != null) {
            pathEncoding = pathNameEncoding;
        }
        if (!eof())
            parseEntry();
    }

