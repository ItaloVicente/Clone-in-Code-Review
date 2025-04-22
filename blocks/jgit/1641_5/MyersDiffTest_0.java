
	public void testPerformanceTestDeltaLength() {
		String a = DiffTestDataGenerator.generateSequence(40000
		String b = DiffTestDataGenerator.generateSequence(40000
		CharArray ac = new CharArray(a);
		CharArray bc = new CharArray(b);
		EditList r = algorithm().diff(new CharCmp()
		assertEquals(131
	}
