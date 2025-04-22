	static RevFilter newRevFilter(BitmapBuilder seen, BitmapBuilder bitmapResult) {
		if (seen != null) {
			return new AddUnseenToBitmapFilter(seen, bitmapResult);
		}
		return new AddToBitmapFilter(bitmapResult);
	}

