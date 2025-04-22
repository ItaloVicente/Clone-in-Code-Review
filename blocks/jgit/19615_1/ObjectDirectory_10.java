		for (AlternateHandle h : myAlternates()) {
			if (primary == null)
				h.db.selectObjectRepresentation(packer
			else if (!h.in(primary.myAlternates())) {
				primary.addAlternate(h);
				h.db.selectObjectRepresentation(packer
			}
		}
