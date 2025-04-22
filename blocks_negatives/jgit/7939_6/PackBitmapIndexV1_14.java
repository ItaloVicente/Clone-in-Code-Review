		@Override
		public EWAHCompressedBitmap ofObjectType(
				EWAHCompressedBitmap bitmap, int type) {
			switch (type) {
			case Constants.OBJ_BLOB:
				return blobs.and(bitmap);
			case Constants.OBJ_TREE:
				return trees.and(bitmap);
			case Constants.OBJ_COMMIT:
				return commits.and(bitmap);
			case Constants.OBJ_TAG:
				return tags.and(bitmap);
			}
			throw new IllegalArgumentException();
		}
