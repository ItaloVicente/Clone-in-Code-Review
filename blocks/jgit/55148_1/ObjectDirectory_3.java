	private boolean isMine(AlternateHandle ah) {
		if (ah.db == null)
			return false;
		if (getAlternateId() == null)
			return false;
		return getAlternateId().equals(ah.db.getAlternateId());
	}


