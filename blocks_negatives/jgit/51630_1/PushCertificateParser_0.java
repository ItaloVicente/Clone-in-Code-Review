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
