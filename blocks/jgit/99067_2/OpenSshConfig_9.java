

		private void complete(String initialHostName
			hostName = config.getHostname();
			user = config.getUser();
			port = config.getPort();
			connectionAttempts = positive(
			if (value != null) {
				batchMode = yesno(value);
			}
			if (value != null) {
				preferredAuthentications = nows(value);
			}
			if (hostName == null) {
				hostName = initialHostName;
			}
			if (user == null) {
				user = OpenSshConfig.userName();
			}
			if (port <= 0) {
				port = OpenSshConfig.SSH_PORT;
			}
			if (connectionAttempts <= 0) {
				connectionAttempts = 1;
			}
			if (identityFiles != null && identityFiles.length > 0) {
				identityFile = toFile(identityFiles[0]
			}
		}

		private File toFile(String path
				return new File(home
			}
			File ret = new File(path);
			if (ret.isAbsolute()) {
				return ret;
			}
			return new File(home
		}

		Config getConfig() {
			return config;
		}
	}

	@Override
	public Config getConfig(String hostName) {
		Host host = lookup(hostName);
		return host.getConfig();
