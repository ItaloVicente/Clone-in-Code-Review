			if (!skips.contains(alt.getId())) {
				len = alt.db.getPackedSizeFromSelfOrAlternate(curs
				if (0 <= len) {
					return len;
				}
			}
