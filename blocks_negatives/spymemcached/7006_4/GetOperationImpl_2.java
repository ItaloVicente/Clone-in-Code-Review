	@Override
	protected OperationStatus getStatusForErrorCode(int errCode, byte[] errPl) {
        OperationStatus baseStatus = super.getStatusForErrorCode(errCode, errPl);
        if (baseStatus != null) {
            return baseStatus;
        }
        return errCode == ERR_NOT_FOUND ? NOT_FOUND_STATUS : null;
	}

