		viewer.setComparator(new ViewerComparator() {
			@Override
			@SuppressWarnings("unchecked")
			public int compare(Viewer testViewer, Object e1, Object e2) {
				return ((Comparable<Object>) e1).compareTo(e2);
			}
		});
