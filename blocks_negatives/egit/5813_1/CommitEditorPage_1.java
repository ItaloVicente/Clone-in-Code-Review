		Section files = createSection(parent, toolkit, span);
		Composite filesArea = createSectionClient(files, toolkit);

		CommitFileDiffViewer viewer = new CommitFileDiffViewer(filesArea,
				getSite(), SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL
						| SWT.FULL_SELECTION | toolkit.getBorderStyle());
		viewer.getTable().setData(FormToolkit.KEY_DRAW_BORDER,
