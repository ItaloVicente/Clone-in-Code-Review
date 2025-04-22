    public synchronized void broadcast(String fsName,
                                       Path watchable,
                                       List<WatchEvent<?>> events) {
        clusterMessageService.broadcast(ClusterMessageService.DestinationType.PubSub,
                                        getChannelName(fsName),
                                        new WatchEventsWrapper(nodeId,
                                                               fsName,
                                                               watchable,
                                                               events));
    }
