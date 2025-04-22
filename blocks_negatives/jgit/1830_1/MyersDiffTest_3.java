
	public void testPerformanceTestDeltaLength() {
		String a = DiffTestDataGenerator.generateSequence(40000, 971, 3);
		String b = DiffTestDataGenerator.generateSequence(40000, 1621, 5);
		CharArray ac = new CharArray(a);
		CharArray bc = new CharArray(b);
		EditList r = algorithm().diff(new CharCmp(), ac, bc);
		assertEquals(131, r.size());
	}
