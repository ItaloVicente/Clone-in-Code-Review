		return null;
	}

	synchronized boolean tryStealWork(DeltaTask.Slice s) {
		if (s.beginIndex <= cur)
			return false;
		end = s.beginIndex;
		return true;
