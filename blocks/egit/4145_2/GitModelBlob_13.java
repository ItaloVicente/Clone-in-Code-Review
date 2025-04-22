		if ((getKind() & RIGHT) == RIGHT) {
			baseData = new ComparisonDataSource(baseCommit, localId);
			remoteData = new ComparisonDataSource(remoteCommit, remoteId);
		} else /* getKind() == LEFT */{
			baseData = new ComparisonDataSource(remoteCommit, remoteId);
			remoteData = new ComparisonDataSource(baseCommit, localId);
		}
