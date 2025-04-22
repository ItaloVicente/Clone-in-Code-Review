
		for (int i = 0; i < PackExt.values().length; ++i) {
			Integer limit = cfg.getCacheHotMap().get(PackExt.values()[i]);
			if (limit != null && limit.intValue() > 0) {
				cacheHotLimits[i] = limit.intValue();
			} else {
				cacheHotLimits[i] = DfsBlockCacheConfig.DEFAULT_CACHE_HOT_MAX;
			}
		}
