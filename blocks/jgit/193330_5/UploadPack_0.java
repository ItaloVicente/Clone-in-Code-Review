	private void fetchV2(org.eclipse.jgit.transport.PacketLineOut pckOut) throws IOException {
		ProtocolV2Parser parser = new ProtocolV2Parser(transferConfig);
		FetchV2Request req = parser.parseFetchRequest(pckIn);
		currentRequest = req;
		Map<String

