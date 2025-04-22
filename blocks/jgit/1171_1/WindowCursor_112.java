		pin(pack
		position += window.setInput(position
		do {
			int n = inf.inflate(dstbuf
			if (n == 0) {
				if (inf.needsInput()) {
					pin(pack
					position += window.setInput(position
				} else if (inf.finished())
					return dstoff;
				else
					throw new DataFormatException();
			}
			dstoff += n;
		} while (dstoff < dstbuf.length);
		return dstoff;
