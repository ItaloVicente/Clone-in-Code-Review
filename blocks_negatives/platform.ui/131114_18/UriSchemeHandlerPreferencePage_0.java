		TableItem[] tableSchemes = tableViewer.getTable().getItems();
		if (tableSchemes == null) {
			return;
		}
		for (TableItem tableScheme : tableSchemes) {
			UiSchemeInformation schemeInformation = (UiSchemeInformation) (tableScheme.getData());
			if (schemeInformation != null) {
				tableScheme.setChecked(schemeInformation.checked);
			}
