			getShell().addListener(SWT.Resize, new Listener() {
				@Override
				public void handleEvent(Event e) {
					popupSize = getShell().getSize();
					if (infoPopup != null) {
						infoPopup.adjustBounds();
					}
