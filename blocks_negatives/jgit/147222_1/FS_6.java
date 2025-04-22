				Path dir = Files.isDirectory(file) ? file : file.getParent();
				if (!dir.toFile().canWrite()) {
					return FALLBACK_TIMESTAMP_RESOLUTION;
				}
				FileStore s = Files.getFileStore(dir);
				FileStoreAttributeCache c = attributeCache.get(s);
				if (c == null) {
					c = new FileStoreAttributeCache(dir);
					attributeCache.put(s, c);
					if (LOG.isDebugEnabled()) {
						LOG.debug(c.toString());
