			if(bitset.toAdd != null) {
				LOG.error("BITMAP-TOADD: {}"
			}
			if(bitset.toRemove != null) {
				LOG.error("BITMAP_TOREMOVE: {}"
			}

			EWAHCompressedBitmap combinedBitmap = bitset.combine();
			LOG.error("BITMAP: {}"

			EWAHCompressedBitmap curr = combinedBitmap
