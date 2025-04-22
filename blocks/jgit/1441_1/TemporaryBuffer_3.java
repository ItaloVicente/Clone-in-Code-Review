
	private class BlockInputStream extends InputStream {
		private byte[] singleByteBuffer;
		private int blockIndex;
		private Block block;
		private int blockPos;

		BlockInputStream() {
			block = blocks.get(blockIndex);
		}

		@Override
		public int read() throws IOException {
			if (singleByteBuffer == null)
				singleByteBuffer = new byte[1];
			int n = read(singleByteBuffer);
			return n == 1 ? singleByteBuffer[0] & 0xff : -1;
		}

		@Override
		public long skip(long cnt) throws IOException {
			long skipped = 0;
			while (0 < cnt) {
				int n = (int) Math.min(block.count - blockPos
				if (n < 0) {
					blockPos += n;
					skipped += n;
					cnt -= n;
				} else if (nextBlock())
					continue;
				else
					break;
			}
			return skipped;
		}

		@Override
		public int read(byte[] b
			if (len == 0)
				return 0;
			int copied = 0;
			while (0 < len) {
				int c = Math.min(block.count - blockPos
				if (c < 0) {
					System.arraycopy(block.buffer
					blockPos += c;
					off += c;
					len -= c;
				} else if (nextBlock())
					continue;
				else
					break;
			}
			return 0 < copied ? copied : -1;
		}

		private boolean nextBlock() {
			if (++blockIndex < blocks.size()) {
				block = blocks.get(blockIndex);
				blockPos = 0;
				return true;
			}
			return false;
		}
	}
