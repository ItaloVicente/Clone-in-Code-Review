	@Override
	protected void setParentShell(Shell newParentShell) {
		super.setParentShell(newParentShell);
		windowActivationHelper = createWindowActivationHelper(newParentShell);
	}

	protected MouseListener createWindowActivationHelper(final Shell parentShell) {
		return new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (parentShell != null) {
					parentShell.setActive();
					parentShell.moveAbove(null);
				}
			}
		};
	}

