	private void findObjectsToPackUsingBitmaps(
			PackWriterBitmapWalker bitmapWalker
			Set<? extends ObjectId> have)
			throws MissingObjectException
			IOException {
		BitmapBuilder haveBitmap = bitmapWalker.findObjects(have
		bitmapWalker.reset();
		BitmapBuilder wantBitmap = bitmapWalker.findObjects(want
		BitmapBuilder needBitmap = wantBitmap.andNot(haveBitmap);

		if (useCachedPacks && reuseSupport != null
				&& (excludeInPacks == null || excludeInPacks.length == 0))
			cachedPacks.addAll(
					reuseSupport.getCachedPacksAndUpdate(needBitmap));

		for (BitmapObject obj : needBitmap) {
			ObjectId objectId = obj.getObjectId();
			if (exclude(objectId)) {
				needBitmap.remove(objectId);
				continue;
			}
			addObject(objectId
		}

		if (thin)
			haveObjects = haveBitmap;
	}

