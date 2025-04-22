			BitmapIndex index = bitmap.getBitmapIndex();
			Bitmap visitedBitmap;

			if (seen.contains(cmit) || bitmap.contains(cmit)) {
			} else if ((visitedBitmap = index.getBitmap(cmit)) != null) {
				bitmap.or(visitedBitmap);
			} else {
				bitmap.addObject(cmit
