	private static final class MyListSelectionDialog extends ListSelectionDialog {
		private final boolean canCancel;
		private Button checkbox;
		private boolean dontPromptSelection;
		private boolean stillOpenElsewhere;

		private MyListSelectionDialog(Shell shell, Object input, IStructuredContentProvider contentprovider,
				ILabelProvider labelProvider, String message, boolean canCancel, boolean stillOpenElsewhere) {
			super(shell, input, contentprovider, labelProvider, message);
			this.canCancel = canCancel;
			this.stillOpenElsewhere = stillOpenElsewhere;
			int shellStyle = getShellStyle();
			if (!canCancel) {
				shellStyle &= ~SWT.CLOSE;
			}
			setShellStyle(shellStyle | SWT.SHEET);
		}

		public boolean getDontPromptSelection() {
			return dontPromptSelection;
		}

		@Override
		protected void createButtonsForButtonBar(Composite parent) {
			createButton(parent, IDialogConstants.OK_ID, WorkbenchMessages.SaveableHelper_Save_Selected, true);
			if (canCancel) {
				createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
			}
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			Composite dialogAreaComposite = (Composite) super.createDialogArea(parent);

			if (stillOpenElsewhere) {
				Composite checkboxComposite = new Composite(dialogAreaComposite, SWT.NONE);
				checkboxComposite.setLayout(new GridLayout(2, false));

				checkbox = new Button(checkboxComposite, SWT.CHECK);
				checkbox.addSelectionListener(
						widgetSelectedAdapter(e -> dontPromptSelection = checkbox.getSelection()));
				GridData gd = new GridData();
				gd.horizontalAlignment = SWT.BEGINNING;
				checkbox.setLayoutData(gd);

				Label label = new Label(checkboxComposite, SWT.NONE);
				label.setText(WorkbenchMessages.EditorManager_closeWithoutPromptingOption);
				gd = new GridData();
				gd.grabExcessHorizontalSpace = true;
				gd.horizontalAlignment = SWT.BEGINNING;
			}

			return dialogAreaComposite;
		}
	}

