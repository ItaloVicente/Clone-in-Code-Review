
	@SuppressWarnings("unchecked")
	public List<Object> removeResourcesByKeyTypeAndType(Class<?> keyType,
			Class<?>... types) {
		List<Object> removedResources = new ArrayList<Object>();
		for (Class<?> cls : types) {
			Iterator<Map.Entry<?, ?>> iter = getCacheByType(cls).entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<?, ?> entry = iter.next();
				if (keyType.isAssignableFrom(entry.getKey().getClass())) {
					removedResources.add(entry.getValue());
					iter.remove();
				}
			}
		}
		return removedResources;
	}
