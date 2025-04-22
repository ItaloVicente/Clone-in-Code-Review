
    if(errCode == SUCCESS) {
        return STATUS_OK;
    } else {
        StatusCode statusCode = StatusCode.fromBinaryCode(errCode);
        errorMsg = errPl.clone();

        switch (errCode) {
            case ERR_NOT_FOUND:
                return new CASOperationStatus(false, new String(errPl),
                        CASResponse.NOT_FOUND, statusCode);
            case ERR_EXISTS:
                return new CASOperationStatus(false, new String(errPl),
                        CASResponse.EXISTS, statusCode);
            case ERR_NOT_STORED:
                return new CASOperationStatus(false, new String(errPl),
                        CASResponse.NOT_FOUND, statusCode);
            case ERR_2BIG:
            case ERR_INTERNAL:
                handleError(OperationErrorType.SERVER, new String(errPl));
            case ERR_INVAL:
            case ERR_DELTA_BADVAL:
            case ERR_NOT_MY_VBUCKET:
            case ERR_UNKNOWN_COMMAND:
            case ERR_NO_MEM:
            case ERR_NOT_SUPPORTED:
            case ERR_BUSY:
            case ERR_TEMP_FAIL:
                return new OperationStatus(false, new String(errPl), statusCode);
            default:
                return null;
        }
