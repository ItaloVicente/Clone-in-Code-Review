
		final PacketLineIn r = asPacketLineIn(outBuf);
		String master = r.readString();
		int nul = master.indexOf('\0');
		assertTrue("has capability list", nul > 0);
		assertEquals(B.name() + ' ' + R_MASTER, master.substring(0, nul));
		assertSame(PacketLineIn.END, r.readString());

		assertEquals("unpack error Missing blob " + n.name(), r.readString());
		assertEquals("ng refs/heads/s n/a (unpacker error)", r.readString());
		assertSame(PacketLineIn.END, r.readString());
