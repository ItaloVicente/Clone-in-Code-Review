	@Override
	public boolean close() {
		parentShell.getDisplay().removeFilter(SWT.KeyDown, keyListener);
		return super.close();
	}

