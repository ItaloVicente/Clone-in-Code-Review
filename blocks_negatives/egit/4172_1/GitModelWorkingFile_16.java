		super(parent, commit, null, repoId, repoId, null, location);
	}

	@Override
	protected GitCompareInput getCompareInput(ComparisonDataSource baseData,
			ComparisonDataSource remoteData, ComparisonDataSource ancestorData) {
		return new GitLocalCompareInput(getRepository(), ancestorData,
				baseData, remoteData, gitPath);
