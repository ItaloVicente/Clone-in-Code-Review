			final PacketLineIn r = asPacketLineIn(outBuf);
			String master = r.readString();
			int nul = master.indexOf('\0');
			assertTrue("has capability list"
			assertEquals(B.name() + ' ' + R_MASTER
			assertSame(PacketLineIn.END

			assertEquals("unpack error Missing tree " + t.name()
					r.readString());
			assertEquals("ng refs/heads/s n/a (unpacker error)"
					r.readString());
			assertSame(PacketLineIn.END
