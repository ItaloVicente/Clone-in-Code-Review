		try (ReadableChannel rc = ctx.db.openFile(desc, PACK)) {
			ByteBuffer buf = newCopyBuffer(out, rc);
			if (ctx.getOptions().getStreamPackBufferSize() > 0)
				rc.setReadAheadBytes(ctx.getOptions().getStreamPackBufferSize());
			long position = 12;
			long remaining = length - (12 + 20);
			boolean packHeadSkipped = false;
			while (0 < remaining) {
				DfsBlock b = cache.get(key, alignToBlock(position));
				if (b != null) {
					int ptr = (int) (position - b.start);
					int n = (int) Math.min(b.size() - ptr, remaining);
					b.write(out, position, n);
					position += n;
					remaining -= n;
					rc.position(position);
					packHeadSkipped = true;
					continue;
				}

				buf.position(0);
				int n = read(rc, buf);
				if (n <= 0)
