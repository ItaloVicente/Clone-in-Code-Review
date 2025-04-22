			int n = inf.inflate(dstbuf
			if (n == 0) {
				if (headerOnly && dstoff == dstbuf.length)
					return dstoff;
				if (inf.needsInput()) {
					position += block.remaining(position);
					pin(pack
					block.setInput(inf
					continue;
				}
				if (inf.finished())
					return dstoff;
