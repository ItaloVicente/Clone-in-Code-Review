			Arrays.sort(elements, new Comparator() {
				@Override
				public int compare(Object a, Object b) {
					return ViewerComparator.this.compare(viewer, a, b);
				}
			});
