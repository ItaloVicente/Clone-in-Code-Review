
			cmp = a.getType() - b.getType();
			if (cmp != 0) {
				return cmp;
			}

			cmp = (a.getPathHash() >>> 1) - (b.getPathHash() >>> 1);
			if (cmp != 0) {
				return cmp;
			}

			cmp = (a.getPathHash() & 1) - (b.getPathHash() & 1);
			if (cmp != 0) {
				return cmp;
			}

			cmp = (a.isEdge() ? 0 : 1) - (b.isEdge() ? 0 : 1);
			if (cmp != 0) {
				return cmp;
			}

			return b.getWeight() - a.getWeight();
