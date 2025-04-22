				final MUIElement partToTag = minimizedElement;
				final MPart partToActivate = toActivate;

				MenuItem orientationItem = new MenuItem(trimStackMenu, SWT.CASCADE);
				orientationItem.setText(Messages.TrimStack_OrientationMenu);
				Menu orientationMenu = new Menu(orientationItem);
				orientationItem.setMenu(orientationMenu);

				MenuItem defaultItem = new MenuItem(orientationMenu, SWT.RADIO);
				defaultItem.setText(Messages.TrimStack_DefaultOrientationItem);
				defaultItem.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						boolean doRefresh = partToTag.getTags().remove(
								IPresentationEngine.ORIENTATION_HORIZONTAL);
						doRefresh |= partToTag.getTags().remove(
								IPresentationEngine.ORIENTATION_VERTICAL);
						if (isShowing && doRefresh) {
							setPaneLocation(hostPane);
						}
					}
				});
