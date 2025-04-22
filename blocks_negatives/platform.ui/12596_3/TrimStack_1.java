				MenuItem horizontalItem = new MenuItem(orientationMenu, SWT.RADIO);
				horizontalItem.setText(Messages.TrimStack_Horizontal);
				horizontalItem.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						if (!partToTag.getTags().contains(
								IPresentationEngine.ORIENTATION_HORIZONTAL)) {
							partToTag.getTags().remove(IPresentationEngine.ORIENTATION_VERTICAL);
							partToTag.getTags().add(IPresentationEngine.ORIENTATION_HORIZONTAL);
							if (isShowing) {
								setPaneLocation(hostPane);
							}
						}
					}
				});
