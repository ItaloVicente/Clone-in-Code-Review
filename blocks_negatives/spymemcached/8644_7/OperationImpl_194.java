	OperationErrorType classifyError(String line) {
		OperationErrorType rv=null;
		if(line.startsWith("ERROR")) {
			rv=OperationErrorType.GENERAL;
		} else if(line.startsWith("CLIENT_ERROR")) {
			rv=OperationErrorType.CLIENT;
		} else if(line.startsWith("SERVER_ERROR")) {
			rv=OperationErrorType.SERVER;
		}
		return rv;
	}
