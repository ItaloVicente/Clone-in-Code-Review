
	public static @DataPoints
	MergeStrategy[] mergeStrategies = MergeStrategy.get();

	@Theory
	public void testMergeInItself(MergeStrategy mergeStrategy) throws Exception {
