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
		}

		public RefNode getSelectedNode() {
			return selected;
		}
	}

	private static final class BranchLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			RefNode refNode = (RefNode) element;
			return refNode.getObject().getName();
		}

		@Override
		public Image getImage(Object element) {
			return RepositoryTreeNodeType.REF.getIcon();
		}
