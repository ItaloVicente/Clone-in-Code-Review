		Viewer viewer = super.createDiffViewer(parent);
		if (viewer instanceof StructuredViewer) {
			((StructuredViewer) viewer)
					.setComparator(new ViewerComparator(CMP) {

						@Override
						public int category(Object element) {
							if (element instanceof FolderNode) {
								return 0;
							} else {
								return 1;
							}
						}
					});
		}
