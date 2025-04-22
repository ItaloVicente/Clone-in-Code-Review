		walker.reset();
		final BitmapBuilder bitmapResult = bitmapIndex.newBitmapBuilder();

		for (ObjectId obj : start) {
			Bitmap bitmap = bitmapIndex.getBitmap(obj);
			if (bitmap != null)
				bitmapResult.or(bitmap);
		}

		boolean marked = false;
		for (ObjectId obj : start) {
			try {
				if (!bitmapResult.contains(obj)) {
					walker.markStart(walker.parseAny(obj));
					marked = true;
				}
			} catch (MissingObjectException e) {
				if (ignoreMissingStart)
					continue;
				throw e;
			}
		}

		if (marked) {
			if (seen == null) {
				walker.setRevFilter(new AddToBitmapFilter(bitmapResult));
			} else {
				walker.setRevFilter(
						new AddUnseenToBitmapFilter(seen, bitmapResult));
			}
			walker.setObjectFilter(new BitmapObjectFilter(bitmapResult));

			while (walker.next() != null) {
				pm.update(1);
				countOfBitmapIndexMisses++;
			}

			RevObject ro;
			while ((ro = walker.nextObject()) != null) {
				bitmapResult.addObject(ro, ro.getType());
				pm.update(1);
			}
		}

		return bitmapResult;
