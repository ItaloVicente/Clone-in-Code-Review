	@Override
	public int getKind() {
		if (kind != LEFT && kind != RIGHT)
			return kind;

		int changeKind;
		if (zeroId().equals(remoteId))
			changeKind = DELETION;
		else if (zeroId().equals(ancestorId))
			changeKind = ADDITION;
		else
			changeKind = CHANGE;

		kind |= changeKind;

		return kind;
	}

