			LOG.error("Trying to reuse pack {}"

			if (index != null) {
				int numPositions = index.getObjectCount();
				for (int pos = 0; pos < numPositions; pos++) {
					ObjectId objectId = index.getObject(pos);
					EWAHCompressedBitmap bitmap = index.getBitmap(objectId);
					if(bitmap != null) {
						LOG.error("  Bitmap for {}:\n{}"
					}
				}
			}

