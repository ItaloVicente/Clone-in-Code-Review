		MessageDigest md = initCopyPack(out, validate, ctx);
		long p;
		if (cache.shouldCopyThroughCache(length))
			p = copyPackThroughCache(out, ctx, md);
		else
			p = copyPackBypassCache(out, ctx, md);
		verifyPackChecksum(p, md, ctx);
	}

	private MessageDigest initCopyPack(PackOutputStream out, boolean validate,
			DfsReader ctx) throws IOException {
