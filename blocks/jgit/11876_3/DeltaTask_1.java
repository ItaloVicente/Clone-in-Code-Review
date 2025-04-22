		this.tid = tid;
		this.slices = new LinkedList<Slice>();
	}

	void add(Slice s) {
		if (!slices.isEmpty()) {
			Slice last = slices.getLast();
			if (last.endIndex == s.beginIndex) {
				slices.removeLast();
				slices.add(new Slice(last.beginIndex
				return;
			}
		}
		slices.add(s);
