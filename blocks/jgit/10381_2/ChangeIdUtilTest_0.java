		assertEquals(3
				+ "Change-Id: I3b7e4e16b503ce00f07ba6ad01d97a356dad7701\n\n\n"
				"\n"));
		assertEquals(3
										+ "Change-Id: I3b7e4e16b503ce00f07ba6ad01d97a356dad7701\n \n \n"
								"\n"));
		assertEquals(3
				+ "Change-Id:  I3b7e4e16b503ce00f07ba6ad01d97a356dad7701\n"
				"\n"));
		assertEquals(-1
				+ "Change-Id: \n"
		assertEquals(3
				+ "Change-Id: I3b7e4e16b503ce00f07ba6ad01d97a356dad7701 \n"
				"\n"));
		assertEquals(12
				+ "Bug 4711\n"
				+ "Change-Id: I3b7e4e16b503ce00f07ba6ad01d97a356dad7701\n"
				"\n"));
		assertEquals(56
				+ "Change-Id: I3b7e4e16b503ce00f07ba6ad01d97a356dad7701\n"
				+ "\n"
				+ "Change-Id: I3b7e4e16b503ce00f07ba6ad01d97a356dad7701\n"
				"\n"));
		assertEquals(-1
				+ "Change-Id: I3b7e4e16b503ce00f07ba6ad01d97a356dad7701\n"
				+ "\n" + "x\n"
		assertEquals(-1
				+ "Change-Id: I3b7e4e16b503ce00f07ba6ad01d97a356dad7701\n"
				+ "\n" + "x\n"
