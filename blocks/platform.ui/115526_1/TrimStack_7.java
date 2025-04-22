	private Listener escapeListener = event -> {
		if (event.character == SWT.ESC) {
			showStack(false);
			partService.requestActivation();
		}
	};

