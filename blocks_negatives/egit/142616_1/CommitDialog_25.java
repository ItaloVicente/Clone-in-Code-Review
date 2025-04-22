		Collections.sort(items, new Comparator<CommitItem>() {
			@Override
			public int compare(CommitItem o1, CommitItem o2) {
				int diff = o1.status.ordinal() - o2.status.ordinal();
				if (diff != 0)
					return diff;
				return o1.path.compareToIgnoreCase(o2.path);
