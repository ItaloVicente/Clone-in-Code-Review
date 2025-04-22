		HostKey[] hostKeys = hkr.getHostKey(hostName(session));
          
		if (hostKeys == null) {
			return;
		}

		List<String> known = Stream.of(hostKeys
