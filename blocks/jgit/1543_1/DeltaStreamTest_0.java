		assertTrue("bulk read has same content"

		act = new byte[data.length];
		in = open();
		int read = 0;
		while (read < data.length) {
			int n = in.read(act
			if (n <= 0)
				break;
			read += n;
		}
		assertEquals(data.length
		assertEquals(-1
		assertTrue("small reads have same content"
