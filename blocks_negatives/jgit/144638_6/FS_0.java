				FileStore s = Files.getFileStore(dir);
				FileStoreAttributeCache c = attributeCache.get(s);
				if (c == null) {
					c = new FileStoreAttributeCache(s, dir);
					attributeCache.put(s, c);
					if (LOG.isDebugEnabled()) {
						LOG.debug(c.toString());
					}
