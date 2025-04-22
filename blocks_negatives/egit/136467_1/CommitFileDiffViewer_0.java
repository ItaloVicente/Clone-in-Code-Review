		setComparator(new ViewerComparator() {

			@Override
			public int compare(Viewer viewer, Object left, Object right) {
				if (left instanceof FileDiff && right instanceof FileDiff) {
					return FileDiff.PATH_COMPARATOR.compare((FileDiff) left,
							(FileDiff) right);
				}
				return super.compare(viewer, left, right);
			}
		});
