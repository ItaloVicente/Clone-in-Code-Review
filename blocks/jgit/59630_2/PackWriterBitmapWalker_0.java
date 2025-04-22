			return new AddUnseenToBitmapFilter(seen
		}
		return new AddToBitmapFilter(bitmapResult);
	}

	private static class AddToBitmapFilter extends RevFilter {
		private final BitmapBuilder bitmap;

		AddToBitmapFilter(BitmapBuilder bitmap) {
			this.bitmap = bitmap;
