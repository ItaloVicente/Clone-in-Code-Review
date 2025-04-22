    public WatchService newWatchService(String fsName) {
        final JGitWatchService ws = new JGitWatchService(fsName,
                                                         p -> watchServices.remove(p));
        watchServices.add(ws);
        return ws;
    }
