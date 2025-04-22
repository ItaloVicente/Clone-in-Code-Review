
	public List<String> getPushOptions() {
		return pushOptions;

	private static class CheckingSideBandOutputStream extends OutputStream {
		private final InputStream in;
		private final OutputStream out;

		CheckingSideBandOutputStream(InputStream in
			this.in = in;
			this.out = out;
		}

		@Override
		public void write(int b) throws IOException {
			write(new byte[] { (byte) b });
		}

		@Override
		public void write(byte[] buf
			try {
				out.write(buf
			} catch (IOException e) {
				throw checkError(e);
			}
		}

		@Override
		public void flush() throws IOException {
			try {
				out.flush();
			} catch (IOException e) {
				throw checkError(e);
			}
		}

		private IOException checkError(IOException e1) {
			try {
				in.read();
			} catch (TransportException e2) {
				return e2;
			} catch (IOException e2) {
				return e1;
			}
			return e1;
		}
	}
