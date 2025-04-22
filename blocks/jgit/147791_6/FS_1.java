			try {
				path = path.toAbsolutePath();
				Path dir = Files.isDirectory(path) ? path : path.getParent();
				FileStoreAttributes cached = attrCacheByPath.get(dir);
				if (cached != null) {
					return cached;
				}
				FileStoreAttributes attrs = getFileStoreAttributes(dir);
				attrCacheByPath.put(dir
				return attrs;
			} catch (SecurityException e) {
				return FALLBACK_FILESTORE_ATTRIBUTES;
