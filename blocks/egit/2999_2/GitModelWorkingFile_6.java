	@Override
	public int getKind() {
		if (kind != LEFT && kind != RIGHT)
			return kind;

		int changeKind;
		if (zeroId().equals(baseId))
			changeKind = ADDITION;
		else
			changeKind = CHANGE;

		kind |= changeKind;

		return kind;
	}

