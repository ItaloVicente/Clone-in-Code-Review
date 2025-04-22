	private void prepareInflater() {
		if (inf == null)
			inf = InflaterCache.get();
		else
			inf.reset();
	}

