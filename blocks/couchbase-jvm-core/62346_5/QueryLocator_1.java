        if (request instanceof PrelocatedRequest && ((PrelocatedRequest) request).sendTo() != null) {
            InetAddress target = ((PrelocatedRequest) request).sendTo();
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

