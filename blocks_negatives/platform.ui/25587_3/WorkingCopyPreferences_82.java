		HashSet<String> allKeys = new HashSet<String>(Arrays.asList(getOriginal().keys()));
		for (Entry<String, Object> entry : temporarySettings.entrySet()) {
			String key = entry.getKey();
			if (entry.getValue() != null) {
				allKeys.add(key);
			} else {
				allKeys.remove(key);
			}
		}
		return allKeys.toArray(new String[allKeys.size()]);
