    public void createWatchService(String topicName) {
        clusterMessageService.createConsumer(
                ClusterMessageService.DestinationType.PubSub,
                getChannelName(topicName),
                WatchEventsWrapper.class,
                (we) -> {
                    if (!we.getNodeId().equals(nodeId)) {
                        eventsPublisher.accept(we);
                    }
                });
    }
