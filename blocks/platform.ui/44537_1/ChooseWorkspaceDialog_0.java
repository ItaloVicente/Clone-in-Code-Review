
		this.parentShell = parentShell;
		this.keyListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.character == SWT.ESC)
					cancelPressed();
			}
		};
		parentShell.getDisplay().addFilter(SWT.KeyDown, keyListener);
	}

	@Override
	public boolean close() {
		parentShell.getDisplay().removeFilter(SWT.KeyDown, keyListener);
		return super.close();
