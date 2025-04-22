	BitmapBuilder findObjects(Iterable<? extends ObjectId> start
			throws MissingObjectException
				   IOException {
		if (!ignoreMissing) {
			return findObjectsWalk(start
		}

		try {
			return findObjectsWalk(start
		} catch (MissingObjectException ignore) {
		}

		final BitmapBuilder result = bitmapIndex.newBitmapBuilder();
		for (ObjectId obj : start) {
			Bitmap bitmap = bitmapIndex.getBitmap(obj);
			if (bitmap != null) {
				result.or(bitmap);
			}
		}

		for (ObjectId obj : start) {
			if (result.contains(obj)) {
				continue;
			}
			try {
				result.or(findObjectsWalk(Arrays.asList(obj)
			} catch (MissingObjectException ignore) {
			}
		}
		return result;
	}

	private BitmapBuilder findObjectsWalk(Iterable<? extends ObjectId> start
