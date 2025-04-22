	void addAlternate(AlternateHandle alt) {
		AlternateHandle[] alts = myAlternates();
		if (!alt.in(alts)) {
			AlternateHandle[] newalts = new AlternateHandle[alts.length + 1];
			System.arraycopy(alts
			newalts[alts.length] = alt;
			synchronized(alternates) {
				alternates.set(newalts);
			}
		}
	}

