		}
		HostEntry fullConfig = new HostEntry();
		fullConfig.merge(cache.entries.get(HostEntry.DEFAULT_NAME));
		for (final Map.Entry<String
			String key = e.getKey();
			if (isHostMatch(key
				fullConfig.merge(e.getValue());
			}
		}
		h = new Host(fullConfig
		cache.hosts.put(hostName
