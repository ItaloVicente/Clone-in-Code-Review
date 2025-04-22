				if (c != null) {
					return c.getFsTimestampResolution();
				}
				CompletableFuture<FileStoreAttributeCache> f = CompletableFuture
						.supplyAsync(() -> new FileStoreAttributeCache(s
				f.thenAccept(cache -> attributeCache.put(s
				if (!background) {
					FileStoreAttributeCache e = f.get();
