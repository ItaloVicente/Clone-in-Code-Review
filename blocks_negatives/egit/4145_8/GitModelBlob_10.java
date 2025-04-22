	@Override
	public int getKind() {
		if (kind != LEFT && kind != RIGHT)
			return kind;

		int changeKind;
		if (zeroId().equals(baseId))
			changeKind = DELETION;
		else if (zeroId().equals(remoteId) || remoteId == null)
			changeKind = ADDITION;
		else
			changeKind = CHANGE;
