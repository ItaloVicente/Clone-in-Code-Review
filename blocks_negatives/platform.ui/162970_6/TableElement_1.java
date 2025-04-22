	private final ControlSelectedColorCustomization fControlSelectedColorCustomization;

	private static class TableControlSelectionEraseListener extends AbstractControlSelectionEraseListener {

		@Override
		protected void fixEventDetail(Control control, Event event) {
			if ((event.detail & SWT.FOCUSED) != 0 || event.display.getFocusControl() == control) {
				event.detail &= ~SWT.SELECTED;
			}
		}

		@Override
		protected int getNumberOfColumns(Control control) {
			return ((Table) control).getColumnCount();
		}
