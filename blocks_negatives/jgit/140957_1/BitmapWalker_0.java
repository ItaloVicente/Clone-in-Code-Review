	public BitmapBuilder findObjects(Iterable<? extends ObjectId> start, BitmapBuilder seen,
			boolean ignoreMissing)
			throws MissingObjectException, IncorrectObjectTypeException,
				   IOException {
		if (!ignoreMissing) {
			return findObjectsWalk(start, seen, false);
		}

		try {
			return findObjectsWalk(start, seen, true);
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
				result.or(findObjectsWalk(Arrays.asList(obj), result, false));
			} catch (MissingObjectException ignore) {
			}
		}
		return result;
	}

	private BitmapBuilder findObjectsWalk(Iterable<? extends ObjectId> start, BitmapBuilder seen,
			boolean ignoreMissingStart)
