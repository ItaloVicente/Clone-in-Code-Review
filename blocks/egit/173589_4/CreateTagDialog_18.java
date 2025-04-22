	@Nullable
	public Boolean shouldSign() {
		if (signButton == null) {
			return Boolean.FALSE;
		}
		return signExplicit ? Boolean.valueOf(signUser) : null;
	}

