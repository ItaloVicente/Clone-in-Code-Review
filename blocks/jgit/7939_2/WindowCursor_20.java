	@Override
	public BitmapIndex getBitmapIndex() throws IOException {
		for (PackFile pack : db.getPacks()) {
			PackBitmapIndex index = pack.getBitmapIndex();
			if (index != null)
				return new BitmapIndexImpl(index);
		}
		return null;
	}

