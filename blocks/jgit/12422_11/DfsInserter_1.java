		int read(long pos
			int r = 0;
			while (pos < currPos && r < cnt) {
				DfsBlock b = getOrLoadBlock(pos);
				int n = b.copy(pos
				pos += n;
				r += n;
			}
			if (currPos <= pos && r < cnt) {
				int s = (int) (pos - currPos);
				int n = Math.min(currPtr - s
				System.arraycopy(currBuf
				r += n;
			}
			return r;
		}

		byte[] inflate(DfsReader ctx
				DataFormatException {
			byte[] dstbuf;
			try {
				dstbuf = new byte[len];
			} catch (OutOfMemoryError noMemory) {
			}

			Inflater inf = ctx.inflater();
			DfsBlock b = setInput(inf
			for (int dstoff = 0;;) {
				int n = inf.inflate(dstbuf
				if (n > 0)
					dstoff += n;
				else if (inf.needsInput() && b != null) {
					pos += b.remaining(pos);
					b = setInput(inf
				} else if (inf.needsInput())
					throw new EOFException(DfsText.get().unexpectedEofInPack);
				else if (inf.finished())
					return dstbuf;
				else
					throw new DataFormatException();
			}
		}

		private DfsBlock setInput(Inflater inf
			if (pos < currPos) {
				DfsBlock b = getOrLoadBlock(pos);
				b.setInput(inf
				return b;
			}
			inf.setInput(currBuf
			return null;
		}

		private DfsBlock getOrLoadBlock(long pos) throws IOException {
			long s = toBlockStart(pos);
			DfsBlock b = cache.get(packKey
			if (b != null)
				return b;

			byte[] d = new byte[blockSize];
			for (int p = 0; p < blockSize;) {
				int n = out.read(s + p
				if (n <= 0)
					throw new EOFException(DfsText.get().unexpectedEofInPack);
				p += n;
			}
			b = new DfsBlock(packKey
			cache.put(b);
			return b;
		}

		private long toBlockStart(long pos) {
			return (pos / blockSize) * blockSize;
		}

