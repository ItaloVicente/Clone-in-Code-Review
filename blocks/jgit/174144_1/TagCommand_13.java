		boolean setExplicitly = Boolean.TRUE.equals(annotated) || isSigned();
		if (setExplicitly) {
			return true;
		}
		return annotated == null;
	}

	public TagCommand setSigningKey(String signingKey) {
		checkCallable();
		this.signingKey = signingKey;
		return this;
