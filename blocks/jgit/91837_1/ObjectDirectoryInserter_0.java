
	private static class SHA1OutputStream extends FilterOutputStream {
		private final SHA1 md;

		SHA1OutputStream(OutputStream out
			super(out);
			this.md = md;
		}

		@Override
		public void write(int b) throws IOException {
			md.update((byte) b);
			super.write(b);
		}

		@Override
		public void write(byte[] in
			md.update(in
			super.write(in
		}
	}
