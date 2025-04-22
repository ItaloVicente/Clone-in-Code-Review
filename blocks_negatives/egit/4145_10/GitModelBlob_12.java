	private void createCompareInput() {
		if (compareInput == null) {
			ComparisonDataSource baseData;
			ComparisonDataSource remoteData;
			if ((getKind() & RIGHT) == RIGHT) {
				baseData = new ComparisonDataSource(remoteCommit, remoteId);
				remoteData = new ComparisonDataSource(baseCommit, baseId);
			} else /* getKind() == LEFT */{
				baseData = new ComparisonDataSource(baseCommit, baseId);
				remoteData = new ComparisonDataSource(remoteCommit, remoteId);
			}

			ComparisonDataSource ancestorData = new ComparisonDataSource(
					ancestorCommit, ancestorId);
