        if (shouldIBroadcast(broadcastEvents)) {
            jGitEventsBroadcast.broadcast(fsName,
                                          watchable,
                                          elist);
        }
    }
