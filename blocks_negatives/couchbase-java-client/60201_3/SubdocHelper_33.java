
    public static CouchbaseException throwIfDocumentLevelError(ResponseStatus status, String docId, String path) {
        CouchbaseException exception = commonSubdocErrors(status, docId, path);

        switch (status) {
            case SUBDOC_PATH_NOT_FOUND:
            case SUBDOC_PATH_EXISTS:
            case SUBDOC_DELTA_RANGE:
            case SUBDOC_NUM_RANGE:
            case SUBDOC_VALUE_TOO_DEEP:
            case SUBDOC_PATH_TOO_BIG:
            case SUBDOC_PATH_INVALID:
            case SUBDOC_PATH_MISMATCH:
                return exception;
            default:
                throw exception;
        }
    }

