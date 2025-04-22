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
