	public RadioMenu(Menu parent, Model newData) {
		this.parent = parent;
		this.data = newData;

		newData.addChangeListener(this);
	}

	private static boolean isEqual(Object value1, Object value2) {
		if (value1 == null) {
			return value2 == null;
		} else if (value2 == null) {
			return false;
		}

		return value1.equals(value2);
	}

	public void addMenuItem(String text, Object value) {
		MenuItem newItem = new MenuItem(parent, SWT.RADIO);

		newItem.setSelection(isEqual(data.getState(), value));
		newItem.setText(text);
		newItem.setData(value);
		items.add(newItem);

		newItem.addSelectionListener(selectionAdapter);
	}

	public void dispose() {
		Iterator iter = items.iterator();
		while (iter.hasNext()) {
			MenuItem next = (MenuItem) iter.next();

			if (!next.isDisposed()) {
				next.removeSelectionListener(selectionAdapter);
				next.dispose();
			}
		}

		items.clear();
	}

	private void refreshSelection() {
		Iterator iter = items.iterator();
		while (iter.hasNext()) {
			MenuItem next = (MenuItem) iter.next();

			if (!next.isDisposed()) {
				next.setSelection(isEqual(data.getState(), next.getData()));
			}
		}
	}

	@Override
