		walker.setRetainBody(false);

		canBuildBitmaps = config.isBuildBitmaps()
				&& !shallowPack
				&& have.isEmpty()
				&& (excludeInPacks == null || excludeInPacks.length == 0);
		if (!shallowPack && useBitmaps) {
			BitmapIndex bitmapIndex = reader.getBitmapIndex();
			if (bitmapIndex != null) {
				PackWriterBitmapWalker bitmapWalker = new PackWriterBitmapWalker(
						walker
				findObjectsToPackUsingBitmaps(bitmapWalker
				endPhase(countingMonitor);
				stats.timeCounting = System.currentTimeMillis() - countingStart;
				return;
			}
		}

