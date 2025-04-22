        } else if (msg instanceof SubGetRequest) {
            SubGetRequest req =  (SubGetRequest)msg;
            if (req.attributeAccess()) {
                extras.writeByte(SUBDOC_FLAG_XATTR_PATH);
            } else {
                extras.writeByte(0);
            }
        } else if (msg instanceof SubExistRequest) {
            SubExistRequest req =  (SubExistRequest)msg;
            if (req.attributeAccess()) {
                extras.writeByte(SUBDOC_FLAG_XATTR_PATH);
            } else {
                extras.writeByte(0);
            }
