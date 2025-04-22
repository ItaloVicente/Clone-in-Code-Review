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

	public void deselect (int index) {
		if (index < 0 || index >= buttons.length)
			return;
		buttons[index].setSelection(false);
	}

