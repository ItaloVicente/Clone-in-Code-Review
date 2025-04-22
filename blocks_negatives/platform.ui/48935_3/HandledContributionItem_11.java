	public void fill(ToolBar parent, int index) {
		if (model == null) {
			return;
		}
		if (widget != null) {
			return;
		}
		boolean isDropdown = false;
		if (model instanceof MToolItem) {
			MMenu menu = ((MToolItem) model).getMenu();
			isDropdown = menu != null;
		}
		int style = SWT.PUSH;
		if (isDropdown)
			style = SWT.DROP_DOWN;
		else if (model.getType() == ItemType.CHECK)
			style = SWT.CHECK;
		else if (model.getType() == ItemType.RADIO)
			style = SWT.RADIO;
		ToolItem item = null;
		if (index >= 0) {
			item = new ToolItem(parent, style, index);
		} else {
			item = new ToolItem(parent, style);
		}
		item.setData(this);
