    /**
     * Convert the binary protocol status in a typesafe enum that can be acted upon later.
     *
     * @param status the status to convert.
     * @return the converted response status.
     */
    private static ResponseStatus convertStatus(final short status) {
        switch (status) {
            case BinaryMemcacheResponseStatus.SUCCESS:
                return ResponseStatus.SUCCESS;
            default:
                return ResponseStatus.FAILURE;
        }
    }

