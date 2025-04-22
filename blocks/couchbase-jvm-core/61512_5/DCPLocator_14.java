        int nodeId = config.nodeIndexForMaster(dcpRequest.partition());
        if (nodeId == -2) {
            return;
        }
        if (nodeId == -1) {
            RetryHelper.retryOrCancel(env, request, responseBuffer);
            return;
        }
