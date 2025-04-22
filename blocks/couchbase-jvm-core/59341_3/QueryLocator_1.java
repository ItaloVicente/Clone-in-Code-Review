
        return new Node[] {};
    }

    protected boolean checkNode(final Node node) {
        return node.serviceEnabled(ServiceType.QUERY);
