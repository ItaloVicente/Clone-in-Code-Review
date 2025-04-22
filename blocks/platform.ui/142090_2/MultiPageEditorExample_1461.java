		IGotoMarker {

	private TextEditor editor;

	private int editorIndex = 0;

	private Font font;

	private StyledText text;

	public MultiPageEditorExample() {
		super();
	}

	void createPage0() {
		try {
			editor = new TextEditor();
			editorIndex = addPage(editor, getEditorInput());
			setPageText(editorIndex, MessageUtil.getString("Source")); //$NON-NLS-1$
		} catch (PartInitException e) {
			ErrorDialog
					.openError(
							getSite().getShell(),
							MessageUtil.getString("ErrorCreatingNestedEditor"), null, e.getStatus()); //$NON-NLS-1$
		}
	}

	void createPage1() {

		Composite composite = new Composite(getContainer(), SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		layout.numColumns = 2;

		Button fontButton = new Button(composite, SWT.NONE);
		GridData gd = new GridData(GridData.BEGINNING);
		gd.horizontalSpan = 2;
		fontButton.setLayoutData(gd);
		fontButton.setText(MessageUtil.getString("ChangeFont")); //$NON-NLS-1$

		fontButton.addSelectionListener(new SelectionAdapter() {
			@Override
