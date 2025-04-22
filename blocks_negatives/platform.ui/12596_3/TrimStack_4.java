				MenuItem restoreItem = new MenuItem(trimStackMenu, SWT.NONE);
				restoreItem.setText(Messages.TrimStack_RestoreText);
				restoreItem.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						minimizedElement.getTags().remove(IPresentationEngine.MINIMIZED);
						if (partToActivate != null) {
							partService.activate(partToActivate);
						}
