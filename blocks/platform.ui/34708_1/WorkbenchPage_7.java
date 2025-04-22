	private void firePropertyChange(String changeId, Object oldValue, Object newValue) {

		UIListenerLogging.logPagePropertyChanged(this, changeId, oldValue, newValue);

		Object[] listeners = propertyChangeListeners.getListeners();
		PropertyChangeEvent event = new PropertyChangeEvent(this, changeId, oldValue, newValue);

		for (int i = 0; i < listeners.length; i++) {
			((IPropertyChangeListener) listeners[i]).propertyChange(event);
		}
	}

	public IActionBars getActionBars() {
		if (actionBars == null) {
