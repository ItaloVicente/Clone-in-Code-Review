	private void setPreferredKeyType(Session session
		HostKeyRepository hkr = session.getHostKeyRepository();
		for (HostKey hk : hkr.getHostKey(hostName(host
			session.setConfig("server_host_key"
			return;
		}
	}

	private String hostName(String host
		if (port == SSH_PORT) {
			return host;
		}
		return String.format("[%s]:%d"
	}

