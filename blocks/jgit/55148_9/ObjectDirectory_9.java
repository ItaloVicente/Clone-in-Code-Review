			if (!skips.contains(alt.getId())) {
				len = alt.db.getLooseSizeFromSelfOrAlternate(curs
				if (0 <= len) {
					return len;
				}
			}
