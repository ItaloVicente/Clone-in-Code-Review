            })
            .last()
            .map(new Func1<ClusterConfig, Boolean>() {
                @Override
                public Boolean call(ClusterConfig clusterConfig) {
                    return true;
                }
            });
