			dstoff = block.inflate(inf, position, dstbuf, dstoff);

			if (headerOnly && dstoff == dstbuf.length)
				return dstoff;
			if (inf.needsInput()) {
				position += block.remaining(position);
				pin(pack, position);
			} else if (inf.finished())
				return dstoff;
			else
