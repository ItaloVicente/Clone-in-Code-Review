	private void closePart(final Menu menu) {
		MPart selectedPart = (MPart) menu.getData(STACK_SELECTED_PART);
		EPartService partService = getContextForParent(selectedPart).get(EPartService.class);
		if (partService.savePart(selectedPart, true)) {
			partService.hidePart(selectedPart);
		}
	}

	private MenuItem createMenuItem(final Menu menu, String menuItemText, Consumer<SelectionEvent> c) {
		MenuItem menuItem = new MenuItem(menu, SWT.NONE);
		menuItem.setText(menuItemText);
		menuItem.addSelectionListener(SelectionListener.widgetSelectedAdapter(c));
		return menuItem;

	}

