                    for (Node node : nodes) {
                        if (!configNodes.contains(node.hostname())) {
                            removeNode(node);
                            node.disconnect().subscribe();
                        }
                    }
                }
            })
            .map(new Func1<Boolean, ClusterConfig>() {
                @Override
                public ClusterConfig call(Boolean aBoolean) {
                    return config;
                }
            });
