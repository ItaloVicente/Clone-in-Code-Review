
	private final void unlink() {
		prev.next = next;
		next.prev = prev;
	}

	final void makeNext(DeltaWindowEntry e) {
		e.unlink();
		e.next = next;
		e.prev = this;
		next.prev = e;
		next = e;
	}

	static DeltaWindowEntry createWindow(int cnt) {
		DeltaWindowEntry res = new DeltaWindowEntry();
		DeltaWindowEntry p = res;
		for (int i = 0; i < cnt; i++) {
			DeltaWindowEntry e = new DeltaWindowEntry();
			e.prev = p;
			p.next = e;
			p = e;
		}
		p.next = res;
		res.prev = p;
		return res;
	}
