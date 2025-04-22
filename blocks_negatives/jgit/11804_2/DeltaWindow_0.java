		window[resSlot] = window[bestSlot];

		DeltaWindowEntry next = res;
		int slot = prior(resSlot);
		for (; slot != bestSlot; slot = prior(slot)) {
			DeltaWindowEntry e = window[slot];
			window[slot] = next;
			next = e;
		}
		window[slot] = next;
