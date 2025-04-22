		if (cur.indexOf('/') == -1 || dest.indexOf('/') == -1) {
			cur = prefix + cur;
			dest = prefix + dest;
		}

		if (!cur.endsWith(slash)) {
