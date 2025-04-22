			if (n == 0) {
				if (headerOnly && dstoff == dstbuf.length)
					return dstoff;
				if (inf.needsInput()) {
					position += block.remaining(position);
					pin(pack, position);
					block.setInput(inf, position);
					continue;
				}
				if (inf.finished())
					return dstoff;
				throw new DataFormatException();
			}
