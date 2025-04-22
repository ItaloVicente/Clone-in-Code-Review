        if (jGitEventsBroadcast != null) {
            jGitEventsBroadcast.close();
        }
    }

    JGitEventsBroadcast getjGitEventsBroadcast() {
        return jGitEventsBroadcast;
    }

    Map<String, JGitFileSystemWatchServices> getFsWatchServices() {
        return fsWatchServices;
    }
