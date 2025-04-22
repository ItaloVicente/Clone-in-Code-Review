		switch (errCode) {
			case SUCCESS:
				return STATUS_OK;
			case ERR_NOT_FOUND:
				return new CASOperationStatus(false, new String(errPl), CASResponse.NOT_FOUND);
			case ERR_EXISTS:
				return new CASOperationStatus(false, new String(errPl), CASResponse.EXISTS);
			case ERR_NOT_STORED:
				return new CASOperationStatus(false, new String(errPl), CASResponse.NOT_FOUND);
			case ERR_2BIG:
			case ERR_INVAL:
			case ERR_DELTA_BADVAL:
			case ERR_NOT_MY_VBUCKET:
			case ERR_UNKNOWN_COMMAND:
			case ERR_NO_MEM:
			case ERR_NOT_SUPPORTED:
			case ERR_INTERNAL:
			case ERR_BUSY:
			case ERR_TEMP_FAIL:
				return new OperationStatus(false, new String(errPl));
			default:
				return null;
		}
