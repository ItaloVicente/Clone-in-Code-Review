	static Region merge(Region a
		if (a.resultStart > b.resultStart) {
			Region o = a;
			a = b;
			b = o;
		}

		final Region result = a;
		while (b != null) {
			if (a.next != null && a.resultStart < b.resultStart
					&& a.next.resultStart < b.resultStart) {
				a = a.next;
			} else {
				Region bNext = b.next;

				b.next = a.next;
				a.next = b;
				a = b;

				b = bNext;
			}
		}
		return result;
	}

