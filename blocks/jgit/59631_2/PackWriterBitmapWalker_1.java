			Bitmap visitedBitmap;

			if (seen.contains(cmit) || bitmap.contains(cmit)) {
			} else if ((visitedBitmap = bitmap.getBitmapIndex().getBitmap(cmit)) != null) {
				bitmap.or(visitedBitmap);
			} else {
				bitmap.addObject(cmit
