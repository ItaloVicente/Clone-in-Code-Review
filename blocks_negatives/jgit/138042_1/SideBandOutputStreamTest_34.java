		@SuppressWarnings("resource" /* java 7 */)
		final SideBandOutputStream out = new SideBandOutputStream(CH_DATA, 7,
				rawOut);
		out.write('a');
		out.write(new byte[] { 'b', 'c' });
		out.flush();
