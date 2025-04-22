			long size;
			PackBitmapIndex idx;
			ctx.stats.readBitmap++;
			long start = System.nanoTime();
			try (ReadableChannel rc = ctx.db.openFile(desc, BITMAP_INDEX)) {
				try {
					InputStream in = Channels.newInputStream(rc);
					int wantSize = 8192;
					int bs = rc.blockSize();
					if (0 < bs && bs < wantSize)
						bs = (wantSize / bs) * bs;
					else if (bs <= 0)
						bs = wantSize;
					in = new BufferedInputStream(in, bs);
					idx = PackBitmapIndex.read(
							in, idx(ctx), getReverseIdx(ctx));
				} finally {
					size = rc.position();
					ctx.stats.readIdxBytes += size;
					ctx.stats.readIdxMicros += elapsedMicros(start);
