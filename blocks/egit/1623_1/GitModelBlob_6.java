	private void createCompareInput() {
		if (compareInput == null) {
			ComparisonDataSource baseData = new ComparisonDataSource(
					baseCommit, baseId);
			ComparisonDataSource remoteData = new ComparisonDataSource(
					remoteCommit, remoteId);
			ComparisonDataSource ancestorData = new ComparisonDataSource(
					ancestorCommit, ancestorId);
			compareInput = new GitCompareInput(getRepository(), ancestorData,
					baseData, remoteData, gitPath);
