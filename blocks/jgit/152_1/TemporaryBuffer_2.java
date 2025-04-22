			return onDiskFile.length();
		}

		public byte[] toByteArray() throws IOException {
			if (onDiskFile == null) {
				return super.toByteArray();
			}

			final long len = length();
			if (Integer.MAX_VALUE < len)
				throw new OutOfMemoryError("Length exceeds maximum array size");
			final byte[] out = new byte[(int) len];
			final FileInputStream in = new FileInputStream(onDiskFile);
			try {
				IO.readFully(in
			} finally {
				in.close();
			}
			return out;
		}

		public void writeTo(final OutputStream os
				throws IOException {
			if (onDiskFile == null) {
				super.writeTo(os
				return;
			}
			if (pm == null)
				pm = NullProgressMonitor.INSTANCE;
