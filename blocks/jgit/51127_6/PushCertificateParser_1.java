	private static interface StringReader {
		String read() throws EOFException
	}

	private static class PacketLineReader implements StringReader {
		private final PacketLineIn pckIn;

		private PacketLineReader(PacketLineIn pckIn) {
			this.pckIn = pckIn;
		}

		@Override
		public String read() throws IOException {
			return pckIn.readString();
		}
	}

	private static class StreamReader implements StringReader {
		private final Reader reader;

		private StreamReader(Reader reader) {
			this.reader = reader;
		}

		@Override
		public String read() throws IOException {
			String line = IO.readLine(reader
			if (line.isEmpty()) {
				throw new EOFException();
			} else if (line.charAt(line.length() - 1) == '\n') {
				line = line.substring(0
			}
			return line;
		}
	}

	public static PushCertificate fromReader(Reader r)
			throws PackProtocolException
		if (!r.markSupported()) {
			r = new BufferedReader(r);
		}
		PushCertificateParser parser = new PushCertificateParser();
		StreamReader reader = new StreamReader(r);
		parser.receiveHeader(reader);
		String line;
		try {
			while (!(line = reader.read()).isEmpty()) {
				if (line.equals(BEGIN_SIGNATURE)) {
					parser.receiveSignature(reader);
					break;
				}
				parser.addCommand(line);
			}
		} catch (EOFException e) {
		}
		return parser.build();
	}
