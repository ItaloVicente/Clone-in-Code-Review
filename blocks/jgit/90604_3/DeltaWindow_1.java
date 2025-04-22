	private void clearWindowOnTypeSwitch() {
		DeltaWindowEntry p = res.prev;
		if (!p.empty() && res.type() != p.type()) {
			for (; p != res; p = p.prev) {
				clear(p);
			}
		}
	}

