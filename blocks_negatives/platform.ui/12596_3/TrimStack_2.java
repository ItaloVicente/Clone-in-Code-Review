				MenuItem verticalItem = new MenuItem(orientationMenu, SWT.RADIO);
				verticalItem.setText(Messages.TrimStack_Vertical);
				verticalItem.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						if (!partToTag.getTags().contains(IPresentationEngine.ORIENTATION_VERTICAL)) {
							partToTag.getTags().remove(IPresentationEngine.ORIENTATION_HORIZONTAL);
							partToTag.getTags().add(IPresentationEngine.ORIENTATION_VERTICAL);
							if (isShowing) {
								setPaneLocation(hostPane);
							}
						}
					}
				});
