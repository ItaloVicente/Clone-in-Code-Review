    public Observable<Node> getNodes(final GetNodesRequest request) {
        LOGGER.debug("Got instructed to get nodes for Service {}", request.type());
        List<Node> found = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node.hasService(request.type(), request.bucket())) {
                found.add(node);
            }
        }
        return Observable.from(found);
    }

