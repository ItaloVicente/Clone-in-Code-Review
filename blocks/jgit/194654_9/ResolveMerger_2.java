			FileMode mode = newMode == FileMode.MISSING.getBits()
					? FileMode.REGULAR_FILE : FileMode.fromBits(newMode);
			StreamLoader contentLoader = ioHandler.createStreamLoader(rawMerged::openInputStream
					rawMerged.length());
			ioHandler.insertToIndex(contentLoader
					DirCacheEntry.STAGE_0
