		Slot e = cache[hash(position)];
		if (e == null) {
			e = new Slot();
			cache[hash(position)] = e;
		} else {
			clearEntry(e);
		}
