		Arrays.sort(list
			int cmp = (a.isDoNotDelta() ? 1 : 0) - (b.isDoNotDelta() ? 1 : 0);
			if (cmp != 0) {
				return cmp;
			}

			cmp = a.getType() - b.getType();
			if (cmp != 0) {
				return cmp;
