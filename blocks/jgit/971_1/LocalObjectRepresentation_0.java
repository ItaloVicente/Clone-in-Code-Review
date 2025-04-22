	static LocalObjectRepresentation newWhole(PackFile f
		LocalObjectRepresentation r = new LocalObjectRepresentation() {
			@Override
			public int getFormat() {
				return PACK_WHOLE;
			}
		};
		r.pack = f;
		r.offset = p;
		r.length = length;
		return r;
	}
