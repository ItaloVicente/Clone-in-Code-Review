					Arrays.sort(elements, new Comparator<Object>() {
						@Override
						public int compare(Object a, Object b) {
							return ProgressViewerComparator.this.compare(viewer, a, b);
						}
					});
