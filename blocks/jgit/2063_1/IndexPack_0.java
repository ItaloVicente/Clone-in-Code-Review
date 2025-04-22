
	private class InflaterStream extends InputStream {
		private final Inflater inf;

		private final byte[] skipBuffer;

		private Source src;

		private long expectedSize;

		private long actualSize;

		private int p;

		InflaterStream() {
			inf = InflaterCache.get();
			skipBuffer = new byte[512];
		}

		void release() {
			inf.reset();
			InflaterCache.release(inf);
		}

		void open(Source source
			src = source;
			expectedSize = inflatedSize;
			actualSize = 0;

			p = fill(src
			inf.setInput(buf
		}

		@Override
		public long skip(long toSkip) throws IOException {
			long n = 0;
			while (n < toSkip) {
				final int cnt = (int) Math.min(skipBuffer.length
				final int r = read(skipBuffer
				if (r <= 0)
					break;
				n += r;
			}
			return n;
		}

		@Override
		public int read() throws IOException {
			int n = read(skipBuffer
			return n == 1 ? skipBuffer[0] & 0xff : -1;
		}

		@Override
		public int read(byte[] dst
			try {
				int n = 0;
				while (n < cnt) {
					int r = inf.inflate(dst
					if (r == 0) {
						if (inf.finished())
							break;
						if (inf.needsInput()) {
							crc.update(buf
							use(bAvail);

							p = fill(src
							inf.setInput(buf
						} else {
							throw new CorruptObjectException(
									MessageFormat
											.format(
													JGitText.get().packfileCorruptionDetected
													JGitText.get().unknownZlibError));
						}
					} else {
						n += r;
					}
				}
				actualSize += n;
				return 0 < n ? n : -1;
			} catch (DataFormatException dfe) {
				throw new CorruptObjectException(MessageFormat.format(JGitText
						.get().packfileCorruptionDetected
			}
		}

		@Override
		public void close() throws IOException {
			if (read(skipBuffer) != -1 || actualSize != expectedSize) {
				throw new CorruptObjectException(MessageFormat.format(JGitText
						.get().packfileCorruptionDetected
						JGitText.get().wrongDecompressedLength));
			}

			int used = bAvail - inf.getRemaining();
			if (0 < used) {
				crc.update(buf
				use(used);
			}

			inf.reset();
		}
	}
