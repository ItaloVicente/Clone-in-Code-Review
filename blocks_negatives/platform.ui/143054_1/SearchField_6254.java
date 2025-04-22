		txtQuickAccess.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				showList();
			}
		});
		txtQuickAccess.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				table.getDisplay().asyncExec(() -> checkFocusLost(table, txtQuickAccess));
				activated = false;
			}

			@Override
			public void focusGained(FocusEvent e) {
				IHandlerService hs = SearchField.this.window.getContext().get(IHandlerService.class);
				if (commandProvider.getContextSnapshot() == null) {
					commandProvider.setSnapshot(hs.createContextSnapshot(true));
				}
				previousFocusControl = (Control) e.getSource();
				activated = true;
			}
		});
