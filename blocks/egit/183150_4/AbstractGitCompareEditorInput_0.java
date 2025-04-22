		GitDiffTreeViewer viewer = new GitDiffTreeViewer(parent, getContainer(),
				getCompareConfiguration());
		viewer.setComparator(new ViewerComparator(CMP) {

			@Override
			public int category(Object element) {
				if (element instanceof FolderNode) {
					return 0;
				} else {
					return 1;
				}
			}
		});
