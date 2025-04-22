	FetchResultTable(final Composite parent) {
		treePanel = new Composite(parent, SWT.NONE);
		treePanel.setLayout(new GridLayout());
		treeViewer = new TreeViewer(treePanel);
		treeViewer.setLabelProvider(new WorkbenchStyledCellLabelProvider());
		treeViewer.setSorter(new ViewerSorter() {

			public int compare(Viewer viewer, Object e1, Object e2) {
				if (e1 instanceof FetchResultAdapter
						&& e2 instanceof FetchResultAdapter) {
					FetchResultAdapter f1 = (FetchResultAdapter) e1;
					FetchResultAdapter f2 = (FetchResultAdapter) e2;
					if (f1.getChildren(f1).length > 0
							&& f2.getChildren(f2).length == 0)
						return 1;
					if (f1.getChildren(f1).length == 0
							&& f2.getChildren(f2).length > 0)
						return -1;

					return f1.getLabel(f1).compareToIgnoreCase(f2.getLabel(f2));
				}
