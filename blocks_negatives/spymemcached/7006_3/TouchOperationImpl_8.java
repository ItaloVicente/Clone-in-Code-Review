	@Override
	protected OperationStatus getStatusForErrorCode(int errCode, byte[] errPl) {
        OperationStatus baseStatus = super.getStatusForErrorCode(errCode, errPl);
        if (baseStatus != null) {
            return baseStatus;
        }
		OperationStatus rv=null;
		switch(errCode) {
			case ERR_EXISTS:
				rv=EXISTS_STATUS;
				break;
			case ERR_NOT_FOUND:
				rv=NOT_FOUND_STATUS;
				break;
			case ERR_TEMP_FAIL:
				rv=TEMP_FAIL;
				break;
		}
		return rv;
	}

