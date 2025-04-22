	static LocalObjectRepresentation newDelta(PackFile f
			ObjectId base) {
		LocalObjectRepresentation r = new Delta();
		r.pack = f;
		r.offset = p;
		r.length = n;
		r.baseId = base;
		return r;
