public class CTabFolderElement extends CompositeElement implements ChildVisibilityAwareElement {
	private SelectionListener selectionListener = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			applyStyles(getWidget(), true);
		}

	};

