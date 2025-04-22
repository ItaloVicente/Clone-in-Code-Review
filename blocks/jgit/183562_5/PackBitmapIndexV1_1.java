		this.packIndex = packIndexSupplier.get();
		for (int i = 0; i < idxPositionBitmapList.size(); ++i) {
			IdxPositionBitmap idxPositionBitmap = idxPositionBitmapList.get(i);
			ObjectId objectId = packIndex
					.getObjectId(idxPositionBitmap.nthObjectId);
			StoredBitmap sb = new StoredBitmap(objectId
					idxPositionBitmap.bitmap
					idxPositionBitmap.getXorStoredBitmap()
					idxPositionBitmap.flags);
			idxPositionBitmap.sb = sb;
