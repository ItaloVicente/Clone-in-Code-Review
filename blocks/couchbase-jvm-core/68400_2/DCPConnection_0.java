                .filter(new Func1<NodeInfo, Boolean>() {
                    @Override
                    public Boolean call(NodeInfo node) {
                        return node.services().containsKey(ServiceType.DCP);
                    }
                })
