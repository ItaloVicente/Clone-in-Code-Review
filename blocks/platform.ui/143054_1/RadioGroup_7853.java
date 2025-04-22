			potentialNewSelection = getButton(e);
			if (! potentialNewSelection.getSelection()) {
				return;
			}
			if (potentialNewSelection.equals(selectedButton)) {
				return;
			}

			if (fireWidgetChangeSelectionEvent(e)) {
				oldSelection = selectedButton;
				selectedButton = potentialNewSelection;
				if (oldSelection == null) {
					oldSelection = selectedButton;
				}

				fireWidgetSelectedEvent(e);
			}
		}

		private IRadioButton getButton(SelectionEvent e) {
			if (e.data != null) {
				return (IRadioButton) e.data;
			}
			return (IRadioButton) DuckType.implement(IRadioButton.class, e.widget);
		}
	};

	private List<VetoableSelectionListener> widgetChangeListeners = new LinkedList<>();

	protected boolean fireWidgetChangeSelectionEvent(SelectionEvent e) {
		for (VetoableSelectionListener listener : widgetChangeListeners) {
			listener.canWidgetChangeSelection(e);
			if (!e.doit) {
				rollbackSelection();
				return false;
			}
		}
		return true;
	}

	private void rollbackSelection() {
		Display.getCurrent().asyncExec(() -> {
			potentialNewSelection.setSelection(false);
			selectedButton.setSelection(true);
