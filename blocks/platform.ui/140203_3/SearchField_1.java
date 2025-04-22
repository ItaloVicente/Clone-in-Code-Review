		txtQuickAccess.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				showList();
			}
		});
		txtQuickAccess.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (table != null) {
					table.getDisplay().asyncExec(() -> checkFocusLost(table, txtQuickAccess));
				}
				activated = false;
			}

			@Override
			public void focusGained(FocusEvent e) {
				previousFocusControl = (Control) e.getSource();
				activated = true;
			}
		});
		txtQuickAccess.addModifyListener(e -> showList());
		txtQuickAccess.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.ESC) {
					activated = false;
					txtQuickAccess.setText(""); //$NON-NLS-1$
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
