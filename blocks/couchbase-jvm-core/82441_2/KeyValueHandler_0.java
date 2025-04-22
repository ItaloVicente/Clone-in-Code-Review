            SubExistRequest req = (SubExistRequest) msg;
            if (req.xattr()) {
                extras.writeByte(SUBDOC_FLAG_XATTR_PATH);
            } else {
                extras.writeByte(0);
            }
        } else if (msg instanceof SubGetCountRequest) {
            SubGetCountRequest req = (SubGetCountRequest) msg;
