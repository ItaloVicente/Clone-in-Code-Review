			if (updateRequests == null
					|| (updateRequests.contains(key.getPath()))) {
				if (cache.containsKey(key))
					cache.get(key).merge(entry.getValue());
				else
					cache.put(key, entry.getValue());
			}
