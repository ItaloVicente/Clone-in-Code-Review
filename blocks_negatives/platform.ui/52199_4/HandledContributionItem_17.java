			model.setWidget(null);
		}
	}

	private void handleWidgetSelection(Event event) {
		if (widget != null && !widget.isDisposed()) {
			if (dropdownEvent(event)) {
				return;
			}
			if (model.getType() == ItemType.CHECK
					|| model.getType() == ItemType.RADIO) {
				boolean selection = false;
				if (widget instanceof MenuItem) {
					selection = ((MenuItem) widget).getSelection();
				} else if (widget instanceof ToolItem) {
					selection = ((ToolItem) widget).getSelection();
				}
				model.setSelected(selection);
			}
			if (canExecuteItem(event)) {
				executeItem(event);
			}
