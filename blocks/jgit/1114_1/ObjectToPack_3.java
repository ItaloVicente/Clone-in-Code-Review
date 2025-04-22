
	private Deflater deflater() {
		if (deflater == null)
			deflater = new Deflater(writer.getCompressionLevel());
		else
			deflater.reset();
		return deflater;
	}

	static final class ZipStream extends OutputStream {
		private final Deflater deflater;

		private final byte[] zbuf;

		private int outPtr;

		ZipStream(Deflater deflater
			this.deflater = deflater;
			this.zbuf = zbuf;
		}

		int finish() throws IOException {
			deflater.finish();
			for (;;) {
				if (outPtr == zbuf.length)
					throw new EOFException();

				int n = deflater.deflate(zbuf
				if (n == 0) {
					if (deflater.finished())
						return outPtr;
					throw new IOException();
				}
				outPtr += n;
			}
		}

		@Override
		public void write(byte[] b
			deflater.setInput(b
			for (;;) {
				if (outPtr == zbuf.length)
					throw new EOFException();

				int n = deflater.deflate(zbuf
				if (n == 0) {
					if (deflater.needsInput())
						break;
					throw new IOException();
				}
				outPtr += n;
			}
		}

		@Override
		public void write(int b) throws IOException {
			throw new UnsupportedOperationException();
		}
	}
}
