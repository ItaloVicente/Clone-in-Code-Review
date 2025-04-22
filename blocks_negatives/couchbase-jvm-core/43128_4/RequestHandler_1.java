    /**
     * Remove a {@link Node} identified by its hostname.
     *
     * @param hostname the hostname of the node.
     * @return the states of the node (most probably {@link LifecycleState#DISCONNECTED}).
     */
    public Observable<LifecycleState> removeNode(final InetAddress hostname) {
        return removeNode(nodeBy(hostname));
    }

    /**
     * Add the service to the node.
     *
     * @param request the request which contains infos about the service and node to add.
     * @return an observable which contains the newly created service.
     */
    public Observable<Service> addService(final AddServiceRequest request) {
        return nodeBy(request.hostname()).addService(request);
    }

    /**
     * Remove a service from a node.
     *
     * @param request the request which contains infos about the service and node to remove.
     * @return an observable which contains the removed service.
     */
    public Observable<Service> removeService(final RemoveServiceRequest request) {
        return nodeBy(request.hostname()).removeService(request);
    }

