	synchronized DeltaTask.Slice stealWork(DeltaTask.Slice s) {
		int t = s.beginIndex;
		if (t <= cur)
			return null;
		int h = toSearch[cur].getPathHash();
		if (h == toSearch[t].getPathHash())
			return null;
		end = t;
		return s;
	}

