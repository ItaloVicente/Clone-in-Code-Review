	private final IRadioButton[] buttons;
	private final Object[] values;
	IRadioButton oldSelection = null;
	IRadioButton selectedButton = null;
	IRadioButton potentialNewSelection = null;

	public static interface IRadioButton {
		void setData(String string, Object object);

		void addSelectionListener(SelectionListener selectionListener);

		void setSelection(boolean b);

		boolean getSelection();

		boolean isFocusControl();

		String getText();

		void setText(String string);

		void notifyListeners(int eventType, Event object);
	}

	public RadioGroup(Object[] radioButtons, Object[] values) {
		IRadioButton[] buttons = new IRadioButton[radioButtons.length];
		if (buttons.length < 1) {
			throw new IllegalArgumentException("A RadioGroup must manage at least one Button");
		}
		for (int i = 0; i < buttons.length; i++) {
			if (!DuckType.instanceOf(IRadioButton.class, radioButtons[i])) {
				throw new IllegalArgumentException("A radio button was not passed");
			}
			buttons[i] = (IRadioButton) DuckType.implement(IRadioButton.class, radioButtons[i]);
			buttons[i].setData(Integer.toString(i), Integer.valueOf(i));
			buttons[i].addSelectionListener(selectionListener);
		}
		this.buttons = buttons;
		this.values = values;
	}

	public Object getSelection() {
		int selectionIndex = getSelectionIndex();
		if (selectionIndex < 0)
			return "";
		return values[selectionIndex];
	}

	public void setSelection(Object newSelection) {
		deselectAll();
		for (int i = 0; i < values.length; i++) {
			if (values[i].equals(newSelection)) {
				setSelection(i);
				return;
			}
		}
	}

	private SelectionListener selectionListener = new SelectionListener() {
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			potentialNewSelection = getButton(e);
			if (!potentialNewSelection.getSelection()) {
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
