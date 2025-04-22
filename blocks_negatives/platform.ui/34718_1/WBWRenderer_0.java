	class SaveablePartPromptDialog extends Dialog {

		private Collection<MPart> collection;

		private CheckboxTableViewer tableViewer;

		private Object[] checkedElements = new Object[0];

		SaveablePartPromptDialog(Shell shell, Collection<MPart> collection) {
			super(shell);
			this.collection = collection;
		}

		@Override
		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			newShell.setText(SWTRenderersMessages.choosePartsToSaveTitle);
		}


		@Override
		protected Control createDialogArea(Composite parent) {
			parent = (Composite) super.createDialogArea(parent);

			Label label = new Label(parent, SWT.LEAD);
			label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			label.setText(SWTRenderersMessages.choosePartsToSave);

			tableViewer = CheckboxTableViewer.newCheckList(parent, SWT.SINGLE
					| SWT.BORDER);
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.heightHint = 250;
			data.widthHint = 300;
			tableViewer.getControl().setLayoutData(data);
			tableViewer.setLabelProvider(new LabelProvider() {
				@Override
				public String getText(Object element) {
					return ((MPart) element).getLocalizedLabel();
				}
			});
			tableViewer.setContentProvider(ArrayContentProvider.getInstance());
			tableViewer.setInput(collection);
			tableViewer.setAllChecked(true);

			return parent;
		}

		@Override
		public void create() {
			super.create();
			applyDialogStyles(getShell());
		}

