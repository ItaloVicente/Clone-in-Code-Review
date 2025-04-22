	public GitCompareInput(Repository repo,
			ComparisonDataSource ancestroDataSource,
			ComparisonDataSource baseDataSource,
			ComparisonDataSource remoteDataSource, String gitPath) {
		this.repo = repo;
		this.gitPath = gitPath;
		this.baseId = baseDataSource.getObjectId();
		this.remoteId = remoteDataSource.getObjectId();
		this.baseCommit = baseDataSource.getRevCommit();
		this.ancestorId = ancestroDataSource.getObjectId();
		this.remoteCommit = remoteDataSource.getRevCommit();
		this.ancestorCommit = ancestroDataSource.getRevCommit();
		this.name = gitPath.lastIndexOf('/') < 0 ? gitPath : gitPath
				.substring(gitPath.lastIndexOf('/') + 1);
