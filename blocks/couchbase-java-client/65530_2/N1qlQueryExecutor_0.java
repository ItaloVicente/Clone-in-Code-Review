            }).filter(new Func1<NodeInfo, Boolean>() {
                @Override
                public Boolean call(NodeInfo nodeInfo) {
                    return nodeInfo.services().containsKey(ServiceType.QUERY)
                        || nodeInfo.sslServices().containsKey(ServiceType.QUERY);
                }
