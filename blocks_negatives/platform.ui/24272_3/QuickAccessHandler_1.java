		if (searchField == null) {
			return null;
		}
		Control control = (Control) searchField.getWidget();
		if (!((WorkbenchWindow) window).isToolbarVisible()) {
			((WorkbenchWindow) window).toggleToolbarVisibility();
			control = (Control) searchField.getWidget();
		}
		if (((WorkbenchWindow) window).isToolbarVisible() && control != null) {
			Control previousFocusControl = control.getDisplay().getFocusControl();
			control.setFocus();
			SearchField field = (SearchField) searchField.getObject();
			field.activate(previousFocusControl);
