            for (Node node : nodes) {
                if (node.isState(LifecycleState.CONNECTED)) {
                    if (!((GetBucketConfigRequest) request).hostname().equals(node.hostname())) {
                        continue;
                    }
                    return new Node[] { node };
                }
            }
            return new Node[] {};
