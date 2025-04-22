	public Collection<CachedPack> getCachedPacksAndUpdate(
		BitmapBuilder needBitmap) throws IOException {
		for (DfsPackFile pack : db.getPacks()) {
			PackBitmapIndex bitmapIndex = pack.getPackBitmapIndex(this);
			if (needBitmap.removeAllOrNone(bitmapIndex))
				return Collections.<CachedPack> singletonList(
						new DfsCachedPack(pack));
		}
		return Collections.emptyList();
	}

