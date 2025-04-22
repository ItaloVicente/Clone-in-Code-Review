	}

	public void addVetoableSelectionListener(VetoableSelectionListener listener) {
		widgetChangeListeners.add(listener);
	}

	public void removeVetoableSelectionListener(VetoableSelectionListener listener) {
		widgetChangeListeners.remove(listener);
	}

	private List<SelectionListener> widgetSelectedListeners = new ArrayList<>();

	protected void fireWidgetSelectedEvent(SelectionEvent e) {
		for (SelectionListener listener : widgetSelectedListeners) {
			listener.widgetSelected(e);
		}
	}

	protected void fireWidgetDefaultSelectedEvent(SelectionEvent e) {
		fireWidgetSelectedEvent(e);
	}

	public void addSelectionListener(SelectionListener listener) {
		widgetSelectedListeners.add(listener);
	}

	public void removeSelectionListener(SelectionListener listener) {
		widgetSelectedListeners.remove(listener);
	}

	public void deselect(int index) {
		if (index < 0 || index >= buttons.length)
			return;
		buttons[index].setSelection(false);
	}

	public void deselectAll() {
		for (int i = 0; i < buttons.length; i++)
			buttons[i].setSelection(false);
	}

	public int getFocusIndex() {
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].isFocusControl()) {
				return i;
			}
		}
		return -1;
	}

	public String getItem(int index) {
		if (index < 0 || index >= buttons.length)
			SWT.error(SWT.ERROR_INVALID_RANGE, null, "getItem for a nonexistant item");
		return buttons[index].getText();
	}

	public int getItemCount() {
		return buttons.length;
	}

	public String[] getItems() {
		List<String> itemStrings = new ArrayList<>();
		for (IRadioButton button : buttons) {
			itemStrings.add(button.getText());
		}
		return itemStrings.toArray(new String[itemStrings.size()]);
	}

	public Object[] getButtons() {
		return buttons;
	}

	public int getSelectionIndex() {
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].getSelection() == true) {
				return i;
			}
		}
		return -1;
	}

	public int indexOf(String string) {
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].getText().equals(string)) {
				return i;
			}
		}
		return -1;
	}

	public int indexOf(String string, int start) {
		for (int i = start; i < buttons.length; i++) {
			if (buttons[i].getText().equals(string)) {
				return i;
			}
		}
		return -1;
	}

	public boolean isSelected(int index) {
		return buttons[index].getSelection();
	}

	public void select(int index) {
		if (index < 0 || index >= buttons.length)
			return;
		buttons[index].setSelection(true);
	}

	public void setItem(int index, String string) {
		if (index < 0 || index >= buttons.length)
			SWT.error(SWT.ERROR_INVALID_RANGE, null, "setItem for a nonexistant item");
		buttons[index].setText(string);
	}

	public void setSelection(int index) {
		if (index < 0 || index > buttons.length - 1) {
			return;
		}
		buttons[index].setSelection(true);
	}
