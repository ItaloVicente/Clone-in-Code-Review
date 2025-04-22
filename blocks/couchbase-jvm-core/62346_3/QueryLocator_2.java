        if (request instanceof CouchbasePrelocatedRequest && ((CouchbasePrelocatedRequest) request).sendTo() != null) {
            InetAddress target = ((CouchbasePrelocatedRequest) request).sendTo();
            for (Node node : nodes) {
                if (node.hostname().equals(target)
                        && checkNode(node)) {
                    node.send(request);
                    return;
                }
            }

            RetryHelper.retryOrCancel(env, request, responseBuffer);
            return;
        }

