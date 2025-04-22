		try (SideBandOutputStream out = new SideBandOutputStream(CH_DATA
				rawOut)) {
			out.write('a');
			out.write(new byte[] { 'b'
			out.flush();
		}
