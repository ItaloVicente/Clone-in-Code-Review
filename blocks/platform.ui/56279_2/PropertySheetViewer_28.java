				item.setFont(font);
			}
		}

		updatePlus(entry, item);
	}

	private void updatePlus(Object node, TreeItem item) {
		IPropertySheetEntry entry = null;
		PropertySheetCategory category = null;
		if (node instanceof IPropertySheetEntry) {
