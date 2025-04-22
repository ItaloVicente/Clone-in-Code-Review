		out = new SideBandOutputStream(CH_DATA
		out.write('a');
		out.write('b');
		out.write('c');
		out.flush();
		assertBuffer("0008\001abc");
	}

	public void testWrite_SmallBlocks1() throws IOException {
		final SideBandOutputStream out;
		out = new SideBandOutputStream(CH_DATA
