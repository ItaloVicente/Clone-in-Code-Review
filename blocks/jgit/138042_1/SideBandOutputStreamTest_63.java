		try (SideBandOutputStream out = new SideBandOutputStream(CH_DATA
				rawOut)) {
			out.write('a');
			out.write('b');
			out.write('c');
			out.flush();
		}
