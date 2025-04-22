			if (!skips.contains(alt.getId())) {
				ldr = alt.db.openLooseFromSelfOrAlternate(curs
				if (ldr != null) {
					return ldr;
				}
			}
