	public void hasCacheHotMap() throws Exception {
		Map<PackExt, Integer> cacheHotMap = new HashMap<>();
		cacheHotMap.put(PackExt.INDEX, Integer.valueOf(3));
		DfsBlockCache.reconfigure(new DfsBlockCacheConfig().setBlockSize(512)
				.setBlockLimit(512 * 4).setCacheHotMap(cacheHotMap));
		cache = DfsBlockCache.getInstance();

