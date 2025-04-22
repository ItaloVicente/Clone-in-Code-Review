				});

				if (!isEditorStack()) {
					MenuItem closeItem = new MenuItem(trimStackMenu, SWT.NONE);
					closeItem.setText(Messages.TrimStack_CloseText);
					closeItem.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							partService.hidePart((MPart) partToTag);
						}
					});
