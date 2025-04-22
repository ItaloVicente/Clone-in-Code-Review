	public void testWrite_SmallBlocks2() throws IOException {
		final SideBandOutputStream out;
		out = new SideBandOutputStream(CH_DATA
		out.write(new byte[] { 'a'
		out.flush();
		assertBuffer("0006\001a0006\001b0006\001c");
	}

	public void testWrite_SmallBlocks3() throws IOException {
		final SideBandOutputStream out;
		out = new SideBandOutputStream(CH_DATA
		out.write('a');
		out.write(new byte[] { 'b'
		out.flush();
		assertBuffer("0007\001ab0006\001c");
	}

