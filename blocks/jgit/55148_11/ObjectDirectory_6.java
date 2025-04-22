			if (!skips.contains(alt.getId())) {
				ldr = alt.db.openPackedFromSelfOrAlternate(curs
				if (ldr != null) {
					return ldr;
				}
			}
