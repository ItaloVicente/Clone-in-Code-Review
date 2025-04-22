	boolean isFillAligned(Control control) {
		Object layoutData = control.getLayoutData();

		if (layoutData instanceof TableWrapData) {
			TableWrapData tableWrapData = (TableWrapData) layoutData;

			if (tableWrapData.align == TableWrapData.FILL) {
				return true;
			}
		}
