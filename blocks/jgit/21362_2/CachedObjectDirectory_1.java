		ObjectLoader ldr = openLooseObject(curs
		if (ldr != null)
			return ldr;
		ldr = wrapped.openPackedObject(curs
		if (ldr != null)
			return ldr;
		for (CachedObjectDirectory alt : myAlternates()) {
			ldr = alt.openObject(curs
			if (ldr != null)
				return ldr;
		}
