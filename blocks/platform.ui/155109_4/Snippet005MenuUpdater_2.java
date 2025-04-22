
	private static Shell createShell() {
		Shell shell = new Shell();

		final WritableList<String> menuItemStrings = new WritableList<>();
		shell.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				System.out.println("adding item");
				menuItemStrings.add(new Date().toString());
				shell.getDisplay().timerExec(5000, this);
			}
		});

		Menu bar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(bar);
		MenuItem fileItem = new MenuItem(bar, SWT.CASCADE);
		fileItem.setText("&Test Menu");
		final Menu submenu = new Menu(shell, SWT.DROP_DOWN);
		fileItem.setMenu(submenu);

		new MenuUpdater(submenu) {
			@Override
			protected void updateMenu() {
				System.out.println("updating menu");
				MenuItem[] items = submenu.getItems();
				int itemIndex = 0;
				for (String s : menuItemStrings) {
					MenuItem item;
					if (itemIndex < items.length) {
						item = items[itemIndex++];
					} else {
						item = new MenuItem(submenu, SWT.NONE);
					}
					item.setText(s);
				}
				while (itemIndex < items.length) {
					items[itemIndex++].dispose();
				}
			}
		};

		shell.open();
		return shell;
	}
