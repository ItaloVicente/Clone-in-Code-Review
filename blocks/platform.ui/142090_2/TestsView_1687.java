		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new TestsViewContentProvider(this));
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(getViewSite());
		getSite().setSelectionProvider(viewer);
	}
