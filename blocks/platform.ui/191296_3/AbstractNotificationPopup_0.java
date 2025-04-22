		final Shell parentShell = display.getActiveShell();
		if (parentShell != null) {
			setParentShell(parentShell);
		}

		windowActivationHelper = new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (parentShell != null) {
					parentShell.setActive();
					parentShell.moveAbove(null);
				}
			}
		};
