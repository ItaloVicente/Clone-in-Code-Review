	private static final class CompressedBitmapBuilder implements BitmapBuilder {
		private ComboBitset bitset;
		private final BitmapIndexImpl bitmapIndex;

		CompressedBitmapBuilder(BitmapIndexImpl bitmapIndex) {
			this.bitset = new ComboBitset();
			this.bitmapIndex = bitmapIndex;
		}
