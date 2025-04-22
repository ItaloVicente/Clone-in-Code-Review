	private String readLine() throws IOException {
		String line = pckIn.readString();
		if (PacketLineIn.isEnd(line)) {
			return null;
		}
			throw new RemoteRepositoryException(uri
		}
		return line;
	}
