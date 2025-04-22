		SelectionAdapter s = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String text = ((Button) e.widget).getText();
				NotificationPopUp notificationPopUp = Snippet081AbstractNotificationPopup.this.new NotificationPopUp(
						display, text);
				notificationPopUp.open();
			}
		};

		shell.setLayout(new FillLayout(SWT.VERTICAL));
		Button a = new Button(shell, SWT.PUSH);
		Button b = new Button(shell, SWT.PUSH);
		Button c = new Button(shell, SWT.PUSH);
		a.setText("Hello World");
		b.setText("I am a notification popup");
		c.setText("Press me!");
		a.addSelectionListener(s);
		b.addSelectionListener(s);
		c.addSelectionListener(s);
		shell.setSize(500, 500);
		shell.open();

