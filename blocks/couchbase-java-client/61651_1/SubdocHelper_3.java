
    public static boolean isSubdocLevelError(ResponseStatus responseStatus) {
        switch(responseStatus) {
            case SUBDOC_PATH_NOT_FOUND:
            case SUBDOC_PATH_EXISTS:
            case SUBDOC_DELTA_RANGE:
            case SUBDOC_NUM_RANGE:
            case SUBDOC_VALUE_TOO_DEEP:
            case SUBDOC_PATH_TOO_BIG:
            case SUBDOC_PATH_INVALID:
            case SUBDOC_PATH_MISMATCH:
            case SUBDOC_VALUE_CANTINSERT:
                return true;
            case SUBDOC_DOC_NOT_JSON:
            case SUBDOC_DOC_TOO_DEEP:
            case SUBDOC_INVALID_COMBO:
            case SUBDOC_MULTI_PATH_FAILURE:
                return false;
            default:
                return false;
        }
    }
