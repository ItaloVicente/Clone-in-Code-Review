	DfsBlock readOneBlock(long pos, DfsReader ctx,
			@Nullable ReadableChannel packChannel) throws IOException {
		if (invalid)
			throw new PackInvalidException(getPackName());

		ctx.stats.readBlock++;
		long start = System.nanoTime();
		ReadableChannel rc = packChannel != null
				? packChannel
				: ctx.db.openFile(packDesc, PACK);
		try {
			int size = blockSize(rc);
			pos = (pos / size) * size;

			long len = length;
			if (len < 0) {
				len = rc.size();
				if (0 <= len)
					length = len;
			}

			if (0 <= len && len < pos + size)
				size = (int) (len - pos);
			if (size <= 0)
				throw new EOFException(MessageFormat.format(
						DfsText.get().shortReadOfBlock, Long.valueOf(pos),
						getPackName(), Long.valueOf(0), Long.valueOf(0)));

			byte[] buf = new byte[size];
			rc.position(pos);
			int cnt = read(rc, ByteBuffer.wrap(buf, 0, size));
			ctx.stats.readBlockBytes += cnt;
			if (cnt != size) {
				if (0 <= len) {
					throw new EOFException(MessageFormat.format(
						    DfsText.get().shortReadOfBlock,
						    Long.valueOf(pos),
						    getPackName(),
						    Integer.valueOf(size),
						    Integer.valueOf(cnt)));
				}

				byte[] n = new byte[cnt];
				System.arraycopy(buf, 0, n, 0, n.length);
				buf = n;
			} else if (len < 0) {
				length = len = rc.size();
			}

			return new DfsBlock(key, pos, buf);
		} finally {
			if (rc != packChannel) {
				rc.close();
			}
			ctx.stats.readBlockMicros += elapsedMicros(start);
		}
	}

	private int blockSize(ReadableChannel rc) {
		int size = blockSize;
		if (size == 0) {
			size = rc.blockSize();
			if (size <= 0)
				size = cache.getBlockSize();
			else if (size < cache.getBlockSize())
				size = (cache.getBlockSize() / size) * size;
			blockSize = size;
		}
		return size;
	}

	private static int read(ReadableChannel rc, ByteBuffer buf)
			throws IOException {
		int n;
		do {
			n = rc.read(buf);
		} while (0 < n && buf.hasRemaining());
		return buf.position();
	}

