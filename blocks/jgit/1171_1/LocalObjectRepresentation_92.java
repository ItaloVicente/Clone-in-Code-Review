	static LocalObjectRepresentation newDelta(PackFile f
			long base) {
		LocalObjectRepresentation r = new Delta();
		r.pack = f;
		r.offset = p;
		r.length = n;
		r.baseOffset = base;
		return r;
