	PackBitmapIndex getBitmapIndex() throws IOException {
		PackIndex idx = idx();
		if (idx.hasBitmapIndex())
			return idx.getBitmapIndex(getReverseIdx());
		return null;
	}

