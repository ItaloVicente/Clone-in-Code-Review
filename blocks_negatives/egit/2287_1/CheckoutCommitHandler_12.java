		TableViewer branchesList;

		RefNode selected;

		private BranchMessageDialog(Shell parentShell, List<RefNode> nodes) {
			super(parentShell, UIText.CheckoutHandler_SelectBranchTitle, null,
					UIText.CheckoutHandler_SelectBranchMessage,
					MessageDialog.QUESTION, new String[] {
							IDialogConstants.OK_LABEL,
							IDialogConstants.CANCEL_LABEL }, 0);
			this.nodes = nodes;
		}

		@Override
		protected Control createCustomArea(Composite parent) {
			Composite area = new Composite(parent, SWT.NONE);
			area.setLayoutData(new GridData(GridData.FILL_BOTH));
			area.setLayout(new FillLayout());

			branchesList = new TableViewer(area, SWT.SINGLE | SWT.H_SCROLL
					| SWT.V_SCROLL | SWT.BORDER);
			branchesList.setContentProvider(ArrayContentProvider.getInstance());
			branchesList.setLabelProvider(new BranchLabelProvider());
			branchesList.setInput(nodes);
			branchesList
					.addSelectionChangedListener(new ISelectionChangedListener() {

						public void selectionChanged(SelectionChangedEvent event) {
							getButton(OK).setEnabled(
									!event.getSelection().isEmpty());
						}
					});
			return area;
		}

		@Override
		protected void buttonPressed(int buttonId) {
			if (buttonId == OK)
				selected = (RefNode) ((IStructuredSelection) branchesList
						.getSelection()).getFirstElement();
			super.buttonPressed(buttonId);
		}

		@Override
		public void create() {
			super.create();
			getButton(OK).setEnabled(false);
