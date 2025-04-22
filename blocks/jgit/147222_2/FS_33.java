				if (Files.exists(dir)) {
					s = Files.getFileStore(dir);
					FileStoreAttributes c = attributeCache.get(s);
					if (c != null) {
						return c;
					}
					if (!Files.isWritable(dir)) {
						LOG.debug(
								"{}: cannot measure timestamp resolution in read-only directory {}"
								Thread.currentThread()
						return FALLBACK_FILESTORE_ATTRIBUTES;
