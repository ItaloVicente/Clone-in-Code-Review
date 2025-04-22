	PackBitmapIndex getPackBitmapIndex(DfsReader ctx) throws IOException {
		PackIndex packIndex = idx(ctx);
		if (packIndex.hasBitmapIndex())
			return packIndex.getBitmapIndex(getReverseIdx(ctx));
		return null;
	}

