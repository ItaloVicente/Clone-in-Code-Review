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
						for (Iterator it = menuItemStrings.iterator(); it
								.hasNext();) {
							MenuItem item;
							if (itemIndex < items.length) {
								item = items[itemIndex++];
							} else {
								item = new MenuItem(submenu, SWT.NONE);
							}
							String string = (String) it.next();
							item.setText(string);
						}
						while (itemIndex < items.length) {
							items[itemIndex++].dispose();
