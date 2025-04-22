	public OpenStrategy(Control control) {
		initializeHandler(control.getDisplay());
		addListener(control);
	}

	public void addOpenListener(IOpenEventListener listener) {
		openEventListeners.add(listener);
	}

	public void removeOpenListener(IOpenEventListener listener) {
		openEventListeners.remove(listener);
	}

	public void addSelectionListener(SelectionListener listener) {
		selectionEventListeners.add(listener);
	}

	public void removeSelectionListener(SelectionListener listener) {
		selectionEventListeners.remove(listener);
	}

	public void addPostSelectionListener(SelectionListener listener) {
		postSelectionEventListeners.add(listener);
	}

	public void removePostSelectionListener(SelectionListener listener) {
		postSelectionEventListeners.remove(listener);
	}

	public static int getOpenMethod() {
		return CURRENT_METHOD;
	}

	public static void setOpenMethod(int method) {
		if (method == DOUBLE_CLICK) {
			CURRENT_METHOD = method;
			return;
		}
		if ((method & SINGLE_CLICK) == 0) {
