		@SuppressWarnings("resource" /* java 7 */)
		final SideBandOutputStream out = new SideBandOutputStream(CH_DATA, 6,
				rawOut);
		out.write('a');
		out.write('b');
		out.write('c');
		out.flush();
