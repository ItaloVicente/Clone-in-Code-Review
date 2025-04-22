		if (remoteCommitTime.compareTo(baseCommitTime) > 0)
			kind = LEFT;
		else if (remoteCommitTime.compareTo(baseCommitTime) < 0)
			kind = RIGHT;
		else
			kind = PSEUDO_CONFLICT;

		if (baseId.equals(zeroId()))
