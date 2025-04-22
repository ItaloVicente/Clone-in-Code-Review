		if (!reuse.isEmpty())
			for (BitmapBuilder bitmap : paths)
				bitmap.andNot(reuseBitmap);

		List<BitmapBuilder> distinctPaths = new ArrayList<BitmapBuilder>(paths.size());
		while (!paths.isEmpty()) {
			Collections.sort(paths, BUILDER_BY_CARDINALITY_DSC);
			BitmapBuilder largest = paths.remove(0);
			distinctPaths.add(largest);
