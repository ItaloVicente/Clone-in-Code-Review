	private void reactOnInitialSelection() {
		if (initialSelection instanceof StructuredSelection) {
			reactOnSelection((StructuredSelection) initialSelection);
		}
	}

