		if (length == -1) {
			ctx.pin(this
			ctx.unpin();
		}
		if (cache.shouldCopyThroughCache(length))
			copyPackThroughCache(out
		else
			copyPackBypassCache(out
	}

	private void copyPackThroughCache(PackOutputStream out
			throws IOException {
		long position = 12;
		long remaining = length - (12 + 20);
		while (0 < remaining) {
			DfsBlock b = cache.getOrLoad(this
			int ptr = (int) (position - b.start);
			int n = (int) Math.min(b.size() - ptr
			b.write(out
			position += n;
			remaining -= n;
		}
	}

	private long copyPackBypassCache(PackOutputStream out
			throws IOException {
		try (ReadableChannel rc = ctx.db.openFile(packDesc
			ByteBuffer buf = newCopyBuffer(out
			if (ctx.getOptions().getStreamPackBufferSize() > 0)
				rc.setReadAheadBytes(ctx.getOptions().getStreamPackBufferSize());
			long position = 12;
			long remaining = length - (12 + 20);
			while (0 < remaining) {
				DfsBlock b = cache.get(key
				if (b != null) {
					int ptr = (int) (position - b.start);
					int n = (int) Math.min(b.size() - ptr
					b.write(out
					position += n;
					remaining -= n;
					rc.position(position);
					continue;
				}

				buf.position(0);
				int n = read(rc
				if (n <= 0)
					throw packfileIsTruncated();
				else if (n > remaining)
					n = (int) remaining;
				out.write(buf.array()
				position += n;
				remaining -= n;
			}
			return position;
		}
	}

	private ByteBuffer newCopyBuffer(PackOutputStream out
		int bs = blockSize(rc);
		byte[] copyBuf = out.getCopyBuffer();
		if (bs > copyBuf.length)
			copyBuf = new byte[bs];
		return ByteBuffer.wrap(copyBuf
