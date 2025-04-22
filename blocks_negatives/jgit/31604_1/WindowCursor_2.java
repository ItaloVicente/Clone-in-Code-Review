			if (n == 0) {
				if (inf.needsInput()) {
					pin(pack, position);
					position += window.setInput(position, inf);
				} else if (inf.finished())
					return dstoff;
				else
					throw new DataFormatException();
			}
