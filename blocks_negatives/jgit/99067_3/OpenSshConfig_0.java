
		for (final Map.Entry<String, Host> e : cache.entrySet()) {
			if (!isHostPattern(e.getKey()))
				continue;
			if (!isHostMatch(e.getKey(), hostName))
				continue;
			h.copyFrom(e.getValue());
		}

		if (h.hostName == null)
			h.hostName = hostName;
		if (h.user == null)
			h.user = OpenSshConfig.userName();
		if (h.port == 0)
			h.port = OpenSshConfig.SSH_PORT;
		if (h.connectionAttempts == 0)
			h.connectionAttempts = 1;
		h.patternsApplied = true;
