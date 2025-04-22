		synchronized (packCache) {
			DfsPackFile pack = packCache.get(dsc);
			if (pack != null && pack.invalid()) {
				packCache.remove(dsc);
				pack = null;
			}
			if (pack == null) {
				if (key == null)
					key = new DfsPackKey();
				pack = new DfsPackFile(this, dsc, key);
				packCache.put(dsc, pack);
			}
