				boolean wasVisible = shell.getVisible();
				boolean nowVisible = txtQuickAcesss.getText().length() > 0;
				if (!wasVisible && nowVisible) {
					layoutShell();
					addAccessibleListener();
					quickAccessContents.preOpen();
				}
				if (wasVisible && !nowVisible) {
					removeAccessibleListener();
				}
				if (nowVisible) {
					notifyAccessibleTextChanged();
				}
				shell.setVisible(nowVisible);
