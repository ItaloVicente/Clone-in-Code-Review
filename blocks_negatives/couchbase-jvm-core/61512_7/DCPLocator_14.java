        if (dcpRequest instanceof OpenConnectionRequest) {
            boolean found = false;
            for (NodeInfo nodeInfo : config.nodes()) {
                if (nodeInfo.services().containsKey(ServiceType.DCP)) {
                    for (Node node : nodes) {
                        if (node.hostname().equals(nodeInfo.hostname())) {
                            node.send(request);
                            found = true;
                            break;
                        }
                    }
                }
            }
            if (found) {
                return;
            }
        } else {
            int nodeId = config.nodeIndexForMaster(dcpRequest.partition());
            if (nodeId == -2) {
                return;
            }
            if (nodeId == -1) {
                RetryHelper.retryOrCancel(env, request, responseBuffer);
                return;
            }
