	private static class AddUnseenToBitmapFilter extends NoCommitBodyRevFilter {
		private final BitmapBuilder seen;
		private final BitmapBuilder bitmap;

		AddUnseenToBitmapFilter(BitmapBuilder seen
			this.seen = seen;
			this.bitmap = bitmapResult;
		}
