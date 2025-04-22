		@Override
		public boolean add(AnyObjectId objectId, int type) {
			int position = bitmapIndex.findOrInsert(objectId, type);
			if (bitset.contains(position))
				return false;

			Bitmap entry = bitmapIndex.getBitmap(objectId);
			if (entry != null) {
				or(entry);
				return false;
			}

			bitset.set(position);
			return true;
		}

