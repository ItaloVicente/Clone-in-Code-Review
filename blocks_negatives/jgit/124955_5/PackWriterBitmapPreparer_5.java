				int flags = nextFlg;
				nextIn = nextSpan(distanceFromTip);
				nextFlg = nextIn == distantCommitSpan
						? PackBitmapIndex.FLAG_REUSE : 0;

				BitmapBuilder fullBitmap = commitBitmapIndex.newBitmapBuilder();
				rw.reset();
				rw.markStart(c);
				rw.setRevFilter(new AddUnseenToBitmapFilter(
						selectionHelper.reusedCommitsBitmap, fullBitmap));

				while (rw.next() != null) {
				}
