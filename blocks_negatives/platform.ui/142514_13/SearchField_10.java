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
