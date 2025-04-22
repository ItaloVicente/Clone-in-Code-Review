
		int cacheLimit = ctx.getOptions().getChunkLimit();
		ctx.getRecentChunks().setMaxBytes(0);
		try {
			Prefetcher p = new Prefetcher(ctx
			p.push(Arrays.asList(keyList));
			copyPack(out
		} finally {
			ctx.getRecentChunks().setMaxBytes(cacheLimit);
		}
