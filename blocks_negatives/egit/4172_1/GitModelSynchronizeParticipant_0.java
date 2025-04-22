				ComparisonDataSource baseData = new ComparisonDataSource(
						baseCommit, tw.getObjectId(baseNth));
				ComparisonDataSource remoteData = new ComparisonDataSource(
						remoteCommit, tw.getObjectId(remoteNth));
				return new GitCompareInput(repo, baseData, baseData,
						remoteData, repoRelativeLocation);
