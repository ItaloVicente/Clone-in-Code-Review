	public static void setAttributePathCacheSize(int maxSize
		if (maxSize != 0 && purgeSize != 0 && maxSize <= purgeSize) {
			throw new IllegalStateException(MessageFormat.format(
					JGitText.get().invalidPurgeSize
					Long.valueOf(maxSize)));
		}
		FileStoreAttributes.attrCacheMaxSize.set(maxSize);
		FileStoreAttributes.attrCachePurgeSize.set(purgeSize);
	}

