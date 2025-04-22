		List<MUIElement> elements = null;

		CacheAdapter cache = CacheAdapter
				.getOrCreate(((EObject) searchRoot).eResource());
		CacheKey cacheKey = new CacheKey(id, searchRoot);
		elements = cache.get(cacheKey);

		if (elements == null) {
			elements = findElements(searchRoot, id, MUIElement.class, null);
			cache.set(cacheKey, elements);
