					Arrays.sort(elements,
							byIndex.thenComparing((a, b) -> ProgressViewerComparator.this.compare(viewer, a, b)));
					lastIndexes.clear();
					for (int i = 0; i < elements.length; i++) {
						lastIndexes.put(elements[i], i);
					}
