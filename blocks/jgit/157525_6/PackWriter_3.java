			last = BitmapCommit.copyFrom(cmit).build();
			writeBitmaps.processBitmapForWrite(cmit
					cmit.getFlags());

			walker.setPrevCommit(last);
			walker.setPrevBitmap(bitmap);
