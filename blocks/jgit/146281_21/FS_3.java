	public static void setAttributePathCacheSize(int maxSize
		if (maxSize != 0 && purgeSize != 0 && maxSize <= purgeSize) {
			throw new IllegalStateException(
					"attributePathCacheSizeLimit must be greater than attributePathCachePurgeLimit");
		}
		FileStoreAttributes.attrCacheMaxSize.set(maxSize);
		FileStoreAttributes.attrCachePurgeSize.set(purgeSize);
	}

