	private static void checkNotAdvertisedWantsUsingBitmap(ObjectReader reader,
			BitmapIndex bitmapIndex, List<ObjectId> notAdvertisedWants,
			Set<ObjectId> reachableFrom) throws IOException {
		BitmapWalker bitmapWalker = new BitmapWalker(new ObjectWalk(reader), bitmapIndex, null);
		BitmapBuilder reachables = bitmapWalker.findObjects(reachableFrom, null, false);
		for (ObjectId oid : notAdvertisedWants) {
			if (!reachables.contains(oid)) {
				throw new WantNotValidException(oid);
			}
		}
	}

