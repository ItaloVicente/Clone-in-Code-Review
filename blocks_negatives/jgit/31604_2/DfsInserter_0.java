				if (n > 0)
					dstoff += n;
				else if (inf.needsInput() && b != null) {
					pos += b.remaining(pos);
					b = setInput(inf, pos);
				} else if (inf.needsInput())
					throw new EOFException(DfsText.get().unexpectedEofInPack);
				else if (inf.finished())
