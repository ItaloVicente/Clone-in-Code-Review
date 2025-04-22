                for (Node node : nodes) {
                    if (node.state() == LifecycleState.CONNECTED) {
                        return new Node[] { node };
                    }
                }
                return new Node[] {};
