	private void calculateKind(String ancestorSha1, String baseSha1,
			String remoteSha1) {
		if (ancestorSha1.equals(baseSha1))
			kind = INCOMING;
		else if (ancestorSha1.equals(baseSha1))
			kind = OUTGOING;
		else
			kind = CONFLICTING;

		if (baseSha1.equals(zeroId().getName()))
			kind = kind | ADDITION;
		else if (remoteSha1.equals(zeroId().getName()))
			kind = kind | DELETION;
		else
			kind = kind | CHANGE;
	}

