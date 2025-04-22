	
	public long getPackIndexCacheHits() {
		return stats.idxCacheHit;
	}

	public long getReverseIndexCacheHits() {
		return stats.ridxCacheHit;
	}

	public long getBitmapIndexCacheHits() {
		return stats.bitmapCacheHit;
	}
