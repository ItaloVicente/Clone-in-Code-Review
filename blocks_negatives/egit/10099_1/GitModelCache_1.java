		List<GitModelObject> result = new ArrayList<GitModelObject>();

		for (Entry<String, Change> cacheEntry : cache.entrySet()) {
			GitModelObject entry = extractFromCache(cacheEntry.getValue(), cacheEntry.getKey());
			if (entry == null)
				continue;

			result.add(entry);
		}

		return result.toArray(new GitModelObject[result.size()]);
