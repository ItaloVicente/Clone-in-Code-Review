		int in = Math.min(INFLATE_STRIDE, block.length - ptr);
		if (dstoff < dstbuf.length)
			in = Math.min(in, dstbuf.length - dstoff);
		inf.setInput(block, ptr, in);

		for (;;) {
			int out = inf.inflate(dstbuf, dstoff, dstbuf.length - dstoff);
			if (out == 0) {
				if (inf.needsInput()) {
					ptr += in;
					in = Math.min(INFLATE_STRIDE, block.length - ptr);
					if (in == 0)
						return dstoff;
					inf.setInput(block, ptr, in);
					continue;
				}
				return dstoff;
			}
			dstoff += out;
		}
