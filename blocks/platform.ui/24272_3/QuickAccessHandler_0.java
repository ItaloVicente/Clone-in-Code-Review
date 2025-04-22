		if (searchField != null && searchField.isVisible()) {
			Control control = (Control) searchField.getWidget();
			if (control != null && control.isVisible()) {
				Control previousFocusControl = control.getDisplay().getFocusControl();
				control.setFocus();
				SearchField field = (SearchField) searchField.getObject();
				field.activate(previousFocusControl);
				return null;
			}
