		final Map<String
		final Map<String

		if (!userConfig.isEmpty()) {
			cache = userConfig;
			if (!systemConfig.isEmpty()) {
				for (Entry<String
					String hostName = e.getKey();
					Host host = e.getValue();
					if (!cache.containsKey(hostName)) {
						cache.put(hostName
					} else {
						Host mergedHost = cache.get(hostName);
						mergedHost.copyFrom(host);
						cache.put(hostName
					}
				}
			}
		} else {
			cache = systemConfig;
		}
