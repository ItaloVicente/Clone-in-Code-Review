	private void fetchV2(org.eclipse.jgit.transport.PacketLineOut pckOut) throws IOException {
		org.eclipse.jgit.transport.ProtocolV2Parser parser = new org.eclipse.jgit.transport.ProtocolV2Parser(transferConfig);
		org.eclipse.jgit.transport.FetchV2Request req = parser.parseFetchRequest(pckIn);
		currentRequest = req;
		Map<String

