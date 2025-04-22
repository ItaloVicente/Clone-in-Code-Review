			if (widget instanceof CTabItem) {
				CTabFolder folder = ((CTabItem) widget).getParent();
				if ("selected".equals(pseudo)) {
					CSSSWTColorHelper.setSelectionForeground(folder, newColor);
				} else {
					CSSSWTColorHelper.setForeground(folder, newColor);
				}
			} else if (widget instanceof Control) {
				CSSSWTColorHelper.setForeground((Control) widget, newColor);
