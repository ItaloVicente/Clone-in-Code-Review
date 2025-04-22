	boolean hasOnlySeparators(ToolBar toolbar) {
		ToolItem[] children = toolbar.getItems();
		for (ToolItem toolItem : children) {
			if ((toolItem.getStyle() & SWT.SEPARATOR) == 0) {
				return false;
			} else if (toolItem.getControl() != null
					&& toolItem.getControl().getData(OWNING_ME) instanceof MToolControl) {
				return false;
			}
		}
		return true;
	}

