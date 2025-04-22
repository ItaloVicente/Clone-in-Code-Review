	extends AbstractPropertySection {

	Text fontText;

	private ButtonElement buttonElement;

	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		Assert.isTrue(selection instanceof IStructuredSelection);
		Object input = ((IStructuredSelection) selection).getFirstElement();
		Assert.isTrue(input instanceof ButtonElement);
		this.buttonElement = (ButtonElement) input;
	}

	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		Composite composite = getWidgetFactory()
			.createFlatFormComposite(parent);
		FormData data;

		Shell shell = new Shell();
		GC gc = new GC(shell);
		gc.setFont(shell.getFont());
		Point point = gc.textExtent("");//$NON-NLS-1$
		int buttonHeight = point.y + 5;
		gc.dispose();
		shell.dispose();

		CLabel fontLabel = getWidgetFactory().createCLabel(composite, "Font:"); //$NON-NLS-1$
		fontText = getWidgetFactory().createText(composite, ""); //$NON-NLS-1$
		fontText.setEditable(false);
		Button fontButton = getWidgetFactory().createButton(composite,
			"Change...", SWT.PUSH); //$NON-NLS-1$
		fontButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent event) {
				FontDialog ftDialog = new FontDialog(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell());

				FontData fontdata = buttonElement.getControl().getFont()
					.getFontData()[0];
				String value = fontdata.toString();

				if ((value != null) && (value.length() > 0)) {
					ftDialog.setFontList(new FontData[] {new FontData(value)});
				}
				FontData fData = ftDialog.open();
