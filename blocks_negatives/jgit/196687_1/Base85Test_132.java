	public void testBinary() {
		roundtrip(new byte[] { 1 }, 5);
		roundtrip(new byte[] { 1, 2 }, 5);
		roundtrip(new byte[] { 1, 2, 3 }, 5);
		roundtrip(new byte[] { 1, 2, 3, 4 }, 5);
		roundtrip(new byte[] { 1, 2, 3, 4, 5 }, 10);
		roundtrip(new byte[] { 1, 2, 3, 4, 5, 0, 0, 0 }, 10);
		roundtrip(new byte[] { 1, 2, 3, 4, 0, 0, 0, 5 }, 10);
