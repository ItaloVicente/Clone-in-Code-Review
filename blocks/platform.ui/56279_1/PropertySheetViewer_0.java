			return;
		}

		cellEditor.activate();

		Control control = cellEditor.getControl();
		if (control == null) {
			cellEditor.deactivate();
			cellEditor = null;
			return;
