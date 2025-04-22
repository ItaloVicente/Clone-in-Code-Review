				getSite(), SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL
						| SWT.FULL_SELECTION);
		toolkit.paintBordersFor(viewer.getTable().getParent());
		StackLayout viewerLayout = (StackLayout) viewer.getControl()
				.getParent().getLayout();
		viewerLayout.marginHeight = 2;
		viewerLayout.marginWidth = 2;
