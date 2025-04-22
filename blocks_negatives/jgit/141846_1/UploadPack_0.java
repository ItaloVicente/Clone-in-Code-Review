			walk.setRetainBody(false);
			AsyncRevObjectQueue q = walk.parseAny(notAdvertisedWants, true);
			try {
				RevObject obj;
				while ((obj = q.next()) != null) {
					if (!(obj instanceof RevCommit)) {
						BitmapIndex bitmapIndex = reader.getBitmapIndex();
						if (bitmapIndex != null) {
							checkNotAdvertisedWantsUsingBitmap(
									reader,
									bitmapIndex,
									notAdvertisedWants,
									reachableFrom);
							return;
						}
						throw new WantNotValidException(obj);
					}
					walk.markStart((RevCommit) obj);
