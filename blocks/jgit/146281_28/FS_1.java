			FileStoreAttributes cached = attrCacheByPath.get(dir);
			if (cached != null) {
				return cached;
			}
			FileStoreAttributes attrs = getFileStoreAttributes(dir);
			attrCacheByPath.put(dir
			return attrs;
