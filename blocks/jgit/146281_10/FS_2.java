	public static void setAttributePathCacheSize(
			int attributePathCacheMaxSize
		if (attributePathCacheMaxSize != 0 && attributePathCachePurgeSize != 0
				&& attributePathCacheMaxSize <= attributePathCachePurgeSize) {
			throw new RuntimeException(
					"attributePathCacheSizeLimit must be greater than attributePathCachePurgeLimit");
		}
		FileStoreAttributeCache.attrCacheMaxSize
				.set(attributePathCacheMaxSize);
		FileStoreAttributeCache.attrCachePurgeSize
				.set(attributePathCachePurgeSize);
	}

