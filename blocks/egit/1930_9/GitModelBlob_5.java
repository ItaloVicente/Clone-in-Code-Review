
	protected GitCompareInput getCompareInput(ComparisonDataSource baseData,
			ComparisonDataSource remoteData, ComparisonDataSource ancestorData) {
		return new GitCompareInput(getRepository(), ancestorData, baseData,
				remoteData, gitPath);
	}

