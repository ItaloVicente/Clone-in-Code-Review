
	private interface ErrorWriter {
		void writeError(String message) throws IOException;
	}

	private class SideBandErrorWriter implements ErrorWriter {
		@Override
		public void writeError(String message) throws IOException {
			SideBandOutputStream err = new SideBandOutputStream(
					SideBandOutputStream.CH_ERROR
					SideBandOutputStream.SMALL_BUF
			err.write(Constants.encode(message));
			err.flush();
		}
	}

	private class PackProtocolErrorWriter implements ErrorWriter {
		@Override
		public void writeError(String message) throws IOException {
			new PacketLineOut(requireNonNull(rawOut))
		}
	}
