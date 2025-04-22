					if (!(obj instanceof RevCommit)) {
						BitmapIndex bitmapIndex = reader.getBitmapIndex();
						if (bitmapIndex != null) {
							checkNotAdvertisedWantsUsingBitmap(
									reader
									bitmapIndex
									notAdvertisedWants
									reachableFrom);
							return;
						}
