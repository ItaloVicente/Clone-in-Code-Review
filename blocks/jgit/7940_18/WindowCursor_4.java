	public Collection<CachedPack> getCachedPacksAndUpdate(
			BitmapBuilder needBitmap) throws IOException {
		for (PackFile pack : db.getPacks()) {
			PackBitmapIndex index = pack.getBitmapIndex();
			if (needBitmap.removeAllOrNone(index))
				return Collections.<CachedPack> singletonList(
						new LocalCachedPack(Collections.singletonList(pack)));
		}
		return Collections.emptyList();
	}

