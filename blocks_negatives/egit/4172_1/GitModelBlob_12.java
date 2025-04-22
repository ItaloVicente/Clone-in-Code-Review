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

			compareInput = getCompareInput(baseData, remoteData, ancestorData);
		}
	}

	/**
	 * Returns specific instance of {@link GitCompareInput} for particular
	 * compare input.
	 *
	 * @param baseData
	 * @param remoteData
	 * @param ancestorData
	 * @return Git specific {@link ICompareInput}
	 */
	protected GitCompareInput getCompareInput(ComparisonDataSource baseData,
			ComparisonDataSource remoteData, ComparisonDataSource ancestorData) {
		return new GitCompareInput(getRepository(), ancestorData, remoteData,
				baseData, gitPath);
	}

