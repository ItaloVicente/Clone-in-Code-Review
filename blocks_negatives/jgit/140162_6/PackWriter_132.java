		Arrays.sort(list, 0, cnt, new Comparator<ObjectToPack>() {
			@Override
			public int compare(ObjectToPack a, ObjectToPack b) {
				int cmp = (a.isDoNotDelta() ? 1 : 0)
						- (b.isDoNotDelta() ? 1 : 0);
				if (cmp != 0)
					return cmp;

				cmp = a.getType() - b.getType();
				if (cmp != 0)
					return cmp;

				cmp = (a.getPathHash() >>> 1) - (b.getPathHash() >>> 1);
				if (cmp != 0)
					return cmp;

				cmp = (a.getPathHash() & 1) - (b.getPathHash() & 1);
				if (cmp != 0)
					return cmp;

				cmp = (a.isEdge() ? 0 : 1) - (b.isEdge() ? 0 : 1);
				if (cmp != 0)
					return cmp;

				return b.getWeight() - a.getWeight();
			}
