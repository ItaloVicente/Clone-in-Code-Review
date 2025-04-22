	private static interface StringReader {
		String read() throws IOException;
	}

	private static class PacketLineReader implements StringReader {
		private final PacketLineIn pckIn;

		private PacketLineReader(PacketLineIn pckIn) {
			this.pckIn = pckIn;
		}

		@Override
		public String read() throws IOException {
			return pckIn.readStringRaw();
		}
	}

	private static class StreamReader implements StringReader {
		private final Reader reader;

		private StreamReader(Reader reader) {
			this.reader = reader;
		}

		@Override
		public String read() throws IOException {
			return IO.readLine(reader
		}
	}

	public static PushCertificate fromReader(Reader r)
			throws PackProtocolException
		PushCertificateParser parser = new PushCertificateParser();
		StreamReader reader = new StreamReader(r);
		parser.receiveHeader(reader);
		String line;
		while (!(line = reader.read()).isEmpty()) {
			if (line.equals(BEGIN_SIGNATURE)) {
				parser.receiveSignature(reader);
				break;
			}
			parser.addCommand(line);
		}
		return parser.build();
	}
