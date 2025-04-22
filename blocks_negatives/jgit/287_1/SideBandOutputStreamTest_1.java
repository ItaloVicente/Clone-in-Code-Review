		new SideBandOutputStream(SideBandOutputStream.CH_DATA,
				new PacketLineOut(mockout)).flush();
		assertEquals(0, flushCnt[0]);

		new SideBandOutputStream(SideBandOutputStream.CH_ERROR,
				new PacketLineOut(mockout)).flush();
