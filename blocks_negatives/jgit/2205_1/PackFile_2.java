		DeltaBaseCache.Entry e = DeltaBaseCache.get(this, posBase);
		if (e != null) {
			base = e.data;
			type = e.type;
		} else {
			ObjectLoader p = load(curs, posBase);
