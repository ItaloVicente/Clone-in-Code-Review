		@SuppressWarnings("resource" /* java 7 */)
		final SideBandOutputStream out = new SideBandOutputStream(CH_DATA,
				SMALL_BUF, rawOut);
		out.write('a');
		out.write('b');
		out.write('c');
		out.flush();
