	@Override
	protected CommonViewer createCommonViewerObject(Composite aParent) {
		return new RepositoriesCommonViewer(getViewSite().getId(), aParent,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
	}

