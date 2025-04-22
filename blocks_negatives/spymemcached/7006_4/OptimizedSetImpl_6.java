	@Override
	protected OperationStatus getStatusForErrorCode(int errCode, byte[] errPl) {
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
			case ERR_NOT_MY_VBUCKET:
				rv = NOT_MY_VBUCKET_STATUS;
				break;
		}
		return rv;
	}

