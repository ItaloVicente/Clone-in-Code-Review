		DeltaBaseCache.Entry e = DeltaBaseCache.get(this, posBase);
		if (e != null) {
			base = e.data;
			type = e.type;
		} else {
			ObjectLoader p = load(curs, posBase);
			try {
				base = p.getCachedBytes(curs.getStreamFileThreshold());
			} catch (LargeObjectException tooBig) {
				return largeDelta(posSelf, hdrLen, posBase, curs);
			}
			type = p.getType();
			DeltaBaseCache.store(this, posBase, base, type);
		}
