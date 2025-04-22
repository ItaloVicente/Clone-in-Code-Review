	private Collection<Key> getRegisteredKeys() {
		List<Key> result = new ArrayList<Key>();
		for (Iterator<Key> i = cacheMap.keySet().iterator(); i.hasNext();) {
			result.add(i.next());
		}
		return result;
	}

