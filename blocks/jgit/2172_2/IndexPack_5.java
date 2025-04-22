		UnresolvedDelta b = reverse(baseByPos.remove(oe.getOffset()));

		if (a == null)
			return b;
		if (b == null)
			return a;

		UnresolvedDelta first = null;
		UnresolvedDelta last = null;
		while (a != null || b != null) {
			UnresolvedDelta n;
			if (b == null || (a != null && a.position < b.position)) {
				n = a;
