	private static void checkNotAdvertisedWantsUsingBitmap(ObjectReader reader
			BitmapIndex bitmapIndex
			Set<ObjectId> reachableFrom) throws IOException {

		BitmapWalker bitmapWalker = new BitmapWalker(new ObjectWalk(reader)
		BitmapBuilder reachables = bitmapWalker.findObjects(reachableFrom
		for (ObjectId oid : notAdvertisedWants) {
			if (!reachables.contains(oid)) {
				throw new WantNotValidException(oid);
			}
		}
	}

