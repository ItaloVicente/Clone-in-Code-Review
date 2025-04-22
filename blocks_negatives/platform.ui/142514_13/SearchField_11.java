		txtQuickAccess.addModifyListener(e -> showList());
		txtQuickAccess.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.ESC) {
					activated = false;
					if (txtQuickAccess == previousFocusControl) {
						txtQuickAccess.getShell().forceFocus();
					} else if (previousFocusControl != null && !previousFocusControl.isDisposed())
						previousFocusControl.setFocus();
				} else if (e.keyCode == SWT.ARROW_UP) {
					e.doit = false;
				} else if (e.keyCode == SWT.ARROW_DOWN) {
					e.doit = false;
				}
				if (e.doit == false) {
					notifyAccessibleTextChanged();
				}
			}
		});
	}

	private void createContentsAndTable() {
		if (quickAccessContents != null) {
			return;
		}
		final CommandProvider commandProvider = new CommandProvider();
