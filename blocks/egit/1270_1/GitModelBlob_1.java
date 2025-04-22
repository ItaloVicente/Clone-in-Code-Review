	private void calculateKind() {
		if (ancestorId.equals(baseId))
			kind = OUTGOING;
		else if (ancestorId.equals(remoteId))
			kind = INCOMING;
		else
			kind = CONFLICTING;

		if (baseId.equals(zeroId()))
			kind = kind | ADDITION;
		else if (remoteId.equals(zeroId()))
			kind = kind | DELETION;
		else
			kind = kind | CHANGE;
	}

