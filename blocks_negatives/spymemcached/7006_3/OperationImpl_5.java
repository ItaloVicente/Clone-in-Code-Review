		if(errorCode != 0) {
			OperationStatus status=getStatusForErrorCode(errorCode, pl);
			if(status == null) {
				handleError(OperationErrorType.SERVER, new String(pl));
			} else if (status == NOT_MY_VBUCKET_STATUS && !getState().equals(OperationState.COMPLETE)) {
                transitionState(OperationState.RETRY);
			} else {
				getCallback().receivedStatus(status);
				transitionState(OperationState.COMPLETE);
			}
		} else {
