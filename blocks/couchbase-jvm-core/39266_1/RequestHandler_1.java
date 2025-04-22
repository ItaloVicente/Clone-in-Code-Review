        if (config.bucketConfigs().values().isEmpty()) {
            return Observable.from(nodes).doOnNext(new Action1<Node>() {
                @Override
                public void call(Node node) {
                    removeNode(node);
                    node.disconnect().subscribe();
                }
            }).last().map(new Func1<Node, ClusterConfig>() {
                @Override
                public ClusterConfig call(Node node) {
                    return config;
                }
            });
        }
