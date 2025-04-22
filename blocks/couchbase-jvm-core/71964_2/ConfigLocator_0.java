    private boolean checkNode(final Node node, final CouchbaseRequest request) {
        if (request instanceof GetDesignDocumentsRequest) {
            return node.serviceEnabled(ServiceType.VIEW);
        } else {
            return node.serviceEnabled(ServiceType.CONFIG);
        }
