		Resource resource = ((EObject) searchRoot).eResource();
		if (resource != null) {
			CacheAdapter cache = CacheAdapter.getOrCreate(resource);
			CacheKey cacheKey = new CacheKey(id, searchRoot);
			elements = cache.get(cacheKey);

			if (elements == null) {
				elements = findElements(searchRoot, id, MUIElement.class, null);
				cache.set(cacheKey, elements);
			}
		} else {
			elements = findElements(searchRoot, id, MUIElement.class, null);
