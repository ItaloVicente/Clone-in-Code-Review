
	@Test
	public void invalidCommand() throws IOException {
		try (Transport tn = testProtocol.open(uri
				InternalPushConnection c = (InternalPushConnection) tn.openPush()) {
			StringWriter msgs = new StringWriter();
			PacketLineOut pckOut = c.pckOut;

			@SuppressWarnings("resource")
			SideBandInputStream in = new SideBandInputStream(c.in
					NullProgressMonitor.INSTANCE

			StringBuilder buf = new StringBuilder();
			buf.append("42");
			buf.append(' ');
			buf.append(obj2.name());
			buf.append(' ');
			buf.append("refs/heads/A" + obj2.name());
			buf.append('\0').append(CAPABILITY_SIDE_BAND_64K);
			buf.append(' ').append(CAPABILITY_REPORT_STATUS);
			buf.append('\n');
			pckOut.writeString(buf.toString());
			pckOut.end();

			try {
				in.read();
				fail("expected TransportException");
			} catch (TransportException e) {
				assertEquals(
						"remote: error: invalid protocol: wanted 'old new ref'"
						e.getMessage());
			}
		}
	}
