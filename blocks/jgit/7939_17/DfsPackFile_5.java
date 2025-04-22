	PackBitmapIndex getPackBitmapIndex(DfsReader ctx) throws IOException {
		DfsBlockCache.Ref<PackBitmapIndex> idxref = bitmapIndex;
		if (idxref != null) {
			PackBitmapIndex idx = idxref.get();
			if (idx != null)
				return idx;
		}

		if (!packDesc.hasFileExt(PackExt.BITMAP_INDEX))
			return null;

		synchronized (initLock) {
			idxref = bitmapIndex;
			if (idxref != null) {
				PackBitmapIndex idx = idxref.get();
				if (idx != null)
					return idx;
			}

			long size;
			PackBitmapIndex idx;
			try {
				ReadableChannel rc = ctx.db.openFile(packDesc
				try {
					InputStream in = Channels.newInputStream(rc);
					int wantSize = 8192;
					int bs = rc.blockSize();
					if (0 < bs && bs < wantSize)
						bs = (wantSize / bs) * bs;
					else if (bs <= 0)
						bs = wantSize;
					in = new BufferedInputStream(in
					idx = PackBitmapIndex.read(
							in
				} finally {
					size = rc.position();
					rc.close();
				}
			} catch (EOFException e) {
				IOException e2 = new IOException(MessageFormat.format(
						DfsText.get().shortReadOfIndex
						packDesc.getFileName(BITMAP_INDEX)));
				e2.initCause(e);
				throw e2;
			} catch (IOException e) {
				IOException e2 = new IOException(MessageFormat.format(
						DfsText.get().cannotReadIndex
						packDesc.getFileName(BITMAP_INDEX)));
				e2.initCause(e);
				throw e2;
			}

			bitmapIndex = cache.put(key
					(int) Math.min(size
			return idx;
		}
	}

