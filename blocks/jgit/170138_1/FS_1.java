				FileStoreAttributes attrs = getFileStoreAttributes(dir
				if (attrs == null) {
					return FALLBACK_FILESTORE_ATTRIBUTES;
				}
				attrCacheByPath.put(dir
				return attrs;
			} catch (SecurityException e) {
				return FALLBACK_FILESTORE_ATTRIBUTES;
			}
		}

		public static FileStoreAttributes get(Path path
			try {
				path = path.toAbsolutePath();
				Path dir = Files.isDirectory(path) ? path : path.getParent();
				FileStoreAttributes cached = attrCacheByPath.get(dir);
				if (cached != null) {
					return cached;
				}
				FileStoreAttributes attrs = getFileStoreAttributes(dir
