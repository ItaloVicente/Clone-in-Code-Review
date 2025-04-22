			if (primary != null) {
				if (!primary.equals(this) && !alt.in(primary.myAlternates())) {
					primary.addAlternate(alt);
					sz = alt.db.getObjectSizeImpl2(curs
							primary);
				}
			} else
				sz = alt.db
						.getObjectSizeImpl2(curs
