	int remaining() {
		return end - cur;
	}

	synchronized DeltaTask.Slice stealWork() {
		for (;;) {
			int e = end;
			int c = cur;
			int n = (e - c) / 2;
			if (0 == n)
				return null;

			int t = e - n;
			int h = toSearch[t].getPathHash();
			while (c < (t - 1)) {
				if (h == toSearch[t - 1].getPathHash())
					t--;
				else
					break;
			}
			if (c != cur)
				continue;
			end = t;
			return new DeltaTask.Slice(t
		}
	}

	private synchronized int end() {
		return end;
	}

	void search() throws IOException {
