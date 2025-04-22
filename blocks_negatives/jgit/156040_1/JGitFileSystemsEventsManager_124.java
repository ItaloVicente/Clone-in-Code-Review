    void setupJGitEventsBroadcast() {
        jGitEventsBroadcast = new JGitEventsBroadcast(clusterMessageService,
                                                      w -> publishEvents(w.getFsName(),
                                                                         w.getWatchable(),
                                                                         w.getEvents(),
                                                                         false));
    }
