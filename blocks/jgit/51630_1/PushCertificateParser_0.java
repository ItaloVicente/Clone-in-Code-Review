	public PushCertificate parse(Reader r)
			throws PackProtocolException
		StreamReader reader = new StreamReader(r);
		receiveHeader(reader);
		String line;
		try {
			while (!(line = reader.read()).isEmpty()) {
				if (line.equals(BEGIN_SIGNATURE)) {
					receiveSignature(reader);
					break;
				}
				addCommand(line);
			}
		} catch (EOFException e) {
		}
		return build();
	}

