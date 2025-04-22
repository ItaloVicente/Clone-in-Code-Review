      if (needsRetry) {
        transitionState(OperationState.RETRY);
      } else {
        getCallback().receivedStatus(STATUS_OK);
        transitionState(OperationState.COMPLETE);
      }
    } else if (errorCode == ERR_NOT_MY_VBUCKET) {
      needsRetry = true;
    } else if (errorCode != SUCCESS) {
