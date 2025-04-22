	private final class PackBitmapIndexImpl extends BasePackBitmapIndex {
		private final PackReverseIndex reverseIndex;

		private PackBitmapIndexImpl(PackReverseIndex reverseIndex) {
			super(bitmaps);
			this.reverseIndex = reverseIndex;
		}

		@Override
		public int findPosition(AnyObjectId objectId) {
			long offset = findOffset(objectId);
			if (offset == -1)
				return -1;
			return reverseIndex.findPostion(offset);
		}

		@Override
		public ObjectId getObject(int position)
				throws IllegalArgumentException {
			ObjectId objectId = reverseIndex.findObjectByPosition(position);
			if (objectId == null)
				throw new IllegalArgumentException();
			return objectId;
		}

		@Override
		public int getObjectCount() {
			return (int) PackIndexVE003.this.getObjectCount();
