	private void calculateKind(String ancestorSha1, String baseSha1,
			String remoteSha1) {
		if (ancestorSha1.equals(baseSha1))
			kind = INCOMING;
		else if (ancestorSha1.equals(baseSha1))
			kind = OUTGOING;
		else
			kind = CONFLICTING;

		if (baseSha1.equals(zeroId().getName()))
