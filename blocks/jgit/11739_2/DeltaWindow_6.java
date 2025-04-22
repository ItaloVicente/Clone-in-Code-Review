	synchronized int remaining() {
		return end - cur;
	}

	synchronized DeltaTask.Slice stealWork() {
		int n = (end - cur) / 2;
		if (0 == n)
			return null;

		int t = end - n;
		int h = toSearch[t].getPathHash();
		while (cur < (t - 1)) {
			if (h == toSearch[t - 1].getPathHash())
				t--;
			else
				break;
		}
		end = t;
		return new DeltaTask.Slice(t
	}

	void search() throws IOException {
