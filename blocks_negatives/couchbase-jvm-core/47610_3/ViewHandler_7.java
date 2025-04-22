    /**
     * Converts a HTTP status code in its appropriate {@link ResponseStatus} representation.
     *
     * @param code the http code.
     * @return the parsed status.
     */
    private static ResponseStatus statusFromCode(int code) {
        ResponseStatus status;
        switch (code) {
            case 200:
            case 201:
                status = ResponseStatus.SUCCESS;
                break;
            case 404:
                status = ResponseStatus.NOT_EXISTS;
                break;
            default:
                status = ResponseStatus.FAILURE;
        }
        return status;
    }

