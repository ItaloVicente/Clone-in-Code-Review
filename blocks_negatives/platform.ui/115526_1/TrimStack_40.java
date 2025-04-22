			closeItem.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					partService.hidePart(selectedPart);
				}
			});
