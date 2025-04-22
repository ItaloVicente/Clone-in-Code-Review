
	@Test
	public void testSpi() {
		List<TransportProtocol> protocols = Transport.getTransportProtocols();
		assertNotNull(protocols);
		assertFalse(protocols.isEmpty());
		TransportProtocol found = null;
		for (TransportProtocol protocol : protocols)
			if (protocol.getSchemes().contains(SpiTransport.SCHEME)) {
				found = protocol;
				break;
			}
		assertEquals(SpiTransport.PROTO
	}
