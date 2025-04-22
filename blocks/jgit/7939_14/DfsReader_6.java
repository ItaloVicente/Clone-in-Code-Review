	@Override
	public BitmapIndex getBitmapIndex() throws IOException {
		for (DfsPackFile pack : db.getPacks()) {
			PackBitmapIndex bitmapIndex = pack.getPackBitmapIndex(this);
			if (bitmapIndex != null)
				return new BitmapIndexImpl(bitmapIndex);
		}
		return null;
	}

