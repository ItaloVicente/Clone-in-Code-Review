
    @Override
    protected boolean checkNode(Node node) {
        return node.serviceEnabled(ServiceType.SEARCH);
    }

