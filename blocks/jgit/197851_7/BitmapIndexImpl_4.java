			boolean missingIndexFound = false;
			while (ii.hasNext()) {
				int pos = ii.next();
				if (pos < bitmapIndex.indexObjectCount) {
					ObjectId posObjectId = bitmapIndex.packIndex.getObject(pos);
					boolean containedInWant = bitset.contains(pos);
					LOG.error("BITMAP-SKIPPED: {}"
					LOG.error("BITMAP-SKIPPED - ObjectId {} found: {} at pos {}"
					missingIndexFound = true;
				}
			}

			if(missingIndexFound) {
