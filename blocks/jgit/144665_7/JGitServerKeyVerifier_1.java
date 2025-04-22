		@Override
		public boolean getHashKnownHosts() {
			HostConfigEntry entry = session.getHostConfigEntry();
			return flag(entry.getProperty(SshConstants.HASH_KNOWN_HOSTS));
		}

