			if (primary != null) {
				if (!primary.equals(this) && !alt.in(primary.myAlternates())) {
					primary.addAlternate(alt);
					sz = alt.db.getObjectSizeImpl1(curs
				}
			} else
				sz = alt.db.getObjectSizeImpl1(curs
