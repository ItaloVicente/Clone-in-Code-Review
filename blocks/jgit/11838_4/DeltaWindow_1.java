
		if (h != toSearch[cur].getPathHash()) {
			for (int p = split - 1; cur < p; p--) {
				if (h != toSearch[p].getPathHash())
					return new DeltaTask.Slice(p + 1
			}
		}
		return null;
	}

	synchronized boolean tryStealWork(DeltaTask.Slice s) {
		if (s.beginIndex <= cur)
			return false;
		end = s.beginIndex;
		return true;
