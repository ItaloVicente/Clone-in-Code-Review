		trimStackTB.addListener(SWT.MenuDetect, new Listener() {
			@Override
			public void handleEvent(Event event) {
				while (trimStackMenu.getItemCount() > 0) {
					trimStackMenu.getItem(0).dispose();
				}
