	protected void propagateChange(PropertyChangeEvent event) {
		if (event.getProperty().equals(IAction.ENABLED)) {
			Boolean bool = (Boolean) event.getNewValue();
			setEnabled(bool.booleanValue());
		} else if (event.getProperty().equals(IAction.CHECKED)) {
			Boolean bool = (Boolean) event.getNewValue();
			setChecked(bool.booleanValue());
		} else if (event.getProperty().equals(SubActionBars.P_ACTION_HANDLERS)) {
			if (event.getSource() instanceof IActionBars) {
				IActionBars bars = (IActionBars) event.getSource();
				setActionHandler(bars.getGlobalActionHandler(getId()));
			}
		}
	}
