	public void shutdown() {
		fsWatchServices.keySet().forEach(key -> this.close(key));

		if (jGitEventsBroadcast != null) {
			jGitEventsBroadcast.close();
		}
	}

	JGitEventsBroadcast getjGitEventsBroadcast() {
		return jGitEventsBroadcast;
	}

	Map<String
		return fsWatchServices;
	}
