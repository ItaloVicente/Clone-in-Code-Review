		PropertySheetViewer ps = getPropertySheet();
		ps.deactivateCellEditor();
		if (isChecked()) {
			ps.showExpert();
		} else {
			ps.hideExpert();
		}
	}
