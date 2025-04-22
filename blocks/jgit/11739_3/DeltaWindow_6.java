	synchronized int remaining() {
		return end - cur;
	}

	synchronized DeltaTask.Slice stealWork() {
		int e = end;
		int n = (e - cur) / 2;
		if (0 == n)
			return null;

		int t = e - n;
		int h = toSearch[t].getPathHash();
		while (cur < t) {
			if (h == toSearch[t - 1].getPathHash())
				t--;
			else
				break;
		}
		end = t;
		return new DeltaTask.Slice(t
	}

	void search() throws IOException {
