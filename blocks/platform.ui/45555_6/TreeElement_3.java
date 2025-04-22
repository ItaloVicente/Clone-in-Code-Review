	private static class TreeControlSelectionEraseListener extends AbstractControlSelectionEraseListener {

		@Override
		protected void fixEventDetail(Control control, Event event) {
			event.detail &= ~SWT.SELECTED;
		}

		@Override
		protected int getNumberOfColumns(Control control) {
			return ((Tree) control).getColumnCount();
		}

	}

	private final ControlSelectedColorCustomization fControlSelectedColorCustomization;

	public TreeElement(Tree tree, CSSEngine engine) {
		super(tree, engine);
		fControlSelectedColorCustomization = new ControlSelectedColorCustomization(tree,
				new TreeControlSelectionEraseListener());
