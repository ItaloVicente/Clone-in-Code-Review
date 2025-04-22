	void setCachedDelta(DeltaCache.Ref data){
		cachedDelta = data;
	}

	DeltaCache.Ref popCachedDelta() {
		DeltaCache.Ref r = cachedDelta;
		if (r != null)
			cachedDelta = null;
		return r;
	}

