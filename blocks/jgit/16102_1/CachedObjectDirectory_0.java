	@Override
	void addAlternate(AlternateHandle alt) {
		if (!alt.in(myAlternates())) {
			AlternateHandle[] newalts = new AlternateHandle[alts.length + 1];
			System.arraycopy(alts
			newalts[alts.length] = alt;
			alts = newalts;
		}
	}

