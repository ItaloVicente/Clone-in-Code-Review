			viewer.setComparator(new ViewerComparator() {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public int compare(Viewer testViewer, Object e1, Object e2) {
					return ((Comparable) e1).compareTo(e2);
				}
			});
