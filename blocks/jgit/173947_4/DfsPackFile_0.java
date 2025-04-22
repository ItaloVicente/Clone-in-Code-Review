		while (cnt > 0) {
			int copied = ctx.copy(this
			if (copied == 0) {
				throw new EOFException();
			}
			position += copied;
			dstoff += copied;
			cnt -= copied;
		}
