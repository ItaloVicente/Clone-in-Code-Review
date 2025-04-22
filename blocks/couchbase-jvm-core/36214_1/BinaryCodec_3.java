    private ResponseStatus convertStatus(short status) {
        switch (status) {
            case BinaryMemcacheResponseStatus.SUCCESS:
                return ResponseStatus.OK;
            case BinaryMemcacheResponseStatus.KEY_EEXISTS:
                return ResponseStatus.EXISTS;
            case BinaryMemcacheResponseStatus.KEY_ENOENT:
                return ResponseStatus.NOT_EXISTS;
            case 0x07: // Represents NOT_MY_VBUCKET
                return ResponseStatus.RETRY;
            default:
                return ResponseStatus.FAILURE;
        }
    }

