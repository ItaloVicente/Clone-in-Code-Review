	
	/**
	 * Asserts that two int arrays are equal in size and contents. If they are not
	 * an AssertionFailedError is thrown.
	 */
	static public void assertEquals(int[] expected, int[] actual) {
		assertEquals(expected.length, actual.length);
		for (int i = 0; i < actual.length; i++) {
		    assertEquals(expected[i], actual[i]);			
