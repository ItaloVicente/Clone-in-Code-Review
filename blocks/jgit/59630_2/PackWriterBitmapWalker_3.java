	private static class AddUnseenToBitmapFilter extends RevFilter {
		private final BitmapBuilder seen;
		private final BitmapBuilder bitmap;

		AddUnseenToBitmapFilter(BitmapBuilder seen
			this.seen = seen;
			this.bitmap = bitmapResult;
		}
