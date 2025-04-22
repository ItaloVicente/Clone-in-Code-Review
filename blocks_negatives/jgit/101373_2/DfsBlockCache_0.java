		DfsBlockCache nc = new DfsBlockCache(cfg);
		DfsBlockCache oc = cache;
		cache = nc;

		if (oc != null) {
			for (DfsPackFile pack : oc.getPackFiles())
				pack.key.cachedSize.set(0);
		}
