
			last = BitmapCommit.copyFrom(cmit).setBitmap(bitmap).build();
			writeBitmaps.processBitmapForWrite(cmit
					cmit.getFlags());

			walker.setBitmapCommit(last);
