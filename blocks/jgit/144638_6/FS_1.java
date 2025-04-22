				if (Files.exists(dir)) {
					s = Files.getFileStore(dir);
					FileStoreAttributeCache c = attributeCache.get(s);
					if (c != null) {
						return c.getFsTimestampResolution();
					}
					if (!Files.isWritable(dir)) {
						return FALLBACK_TIMESTAMP_RESOLUTION;
					}
				} else {
