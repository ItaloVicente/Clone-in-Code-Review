	public CommitFileDiffViewer(final Composite parent,
			final IWorkbenchSite site) {
		this(parent, site, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER
				| SWT.FULL_SELECTION);
	}

	public CommitFileDiffViewer(final Composite parent,
			final IWorkbenchSite site, final int style) {
