			FileStoreAttributeCache c = attrCacheByPath.get(dir);
			if (c != null) {
				return c;
			}
			c = getFileAttributeCache(dir);
			attrCacheByPath.put(dir
			return c;
