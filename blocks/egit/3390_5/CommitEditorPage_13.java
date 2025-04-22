	private void createFilesArea(Composite parent, FormToolkit toolkit, int span) {
		Section files = createSection(parent, toolkit, span);
		Composite filesArea = createSectionClient(files, toolkit);

		TableViewer viewer = new TableViewer(toolkit.createTable(filesArea,
				SWT.V_SCROLL | SWT.H_SCROLL));
