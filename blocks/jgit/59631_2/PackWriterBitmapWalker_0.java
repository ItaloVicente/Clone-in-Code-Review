			Bitmap visitedBitmap;

			if (bitmap.contains(cmit)) {
			} else if ((visitedBitmap = bitmap.getBitmapIndex().getBitmap(cmit)) != null) {
				bitmap.or(visitedBitmap);
			} else {
				bitmap.addObject(cmit
