	private void findObjectsToPackUsingBitmaps(
			PackWriterBitmapWalker bitmapWalker
			Set<? extends ObjectId> want
			throws MissingObjectException
			IOException {
		BitmapBuilder haveBitmap = bitmapWalker.findObjects(have
		bitmapWalker.reset();
		BitmapBuilder wantBitmap = bitmapWalker.findObjects(want
		BitmapBuilder needBitmap = wantBitmap.andNot(haveBitmap);

		if (useCachedPacks && reuseSupport != null
				&& (excludeInPacks == null || excludeInPacks.length == 0)) {
			cachedPacks.addAll(
					reuseSupport.getCachedPacksAndUpdate(needBitmap));
			for (CachedPack cachedPack : cachedPacks)
				pm.update((int) cachedPack.getObjectCount());
		}

		for (BitmapObject obj : needBitmap) {
			ObjectId objectId = obj.getObjectId();
			if (exclude(objectId)) {
				needBitmap.remove(objectId);
				continue;
			}
			addObject(objectId
			pm.update(1);
		}

		if (thin)
			haveObjects = haveBitmap;
	}

