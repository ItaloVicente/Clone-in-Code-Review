      if (retryKeys.size() > 0) {
        transitionState(OperationState.RETRY);
        OperationStatus retryStatus = new OperationStatus(true,
          Integer.toString(retryKeys.size()), StatusCode.ERR_NOT_MY_VBUCKET);
        getCallback().receivedStatus(retryStatus);
        getCallback().complete();
      } else {
        getCallback().receivedStatus(STATUS_OK);
        transitionState(OperationState.COMPLETE);
      }
    } else if (errorCode == ERR_NOT_MY_VBUCKET) {
      retryKeys.add(keys.get(responseOpaque));
    } else if (errorCode != SUCCESS) {
