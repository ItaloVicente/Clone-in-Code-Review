        Node[] locatedNodes = locator(request).locate(request, nodes, config);

        if (locatedNodes == null) {
            return;
        }

        if (locatedNodes.length > 0) {
            for (Node locatedNode : locatedNodes) {
                try {
                    locatedNode.send(request);
                } catch (Exception ex) {
                    request.observable().onError(ex);
                }
            }
        } else {
            RetryHelper.retryOrCancel(environment, request, responseBuffer);
        }
