	@DataPoint
	public static MergeStrategy resolve = MergeStrategy.RESOLVE;

	@Theory
	public void failingPathsShouldNotResultInOKReturnValue(
			MergeStrategy strategy) throws Exception {
