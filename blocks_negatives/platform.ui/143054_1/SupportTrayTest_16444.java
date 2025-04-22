			SupportTray st = new SupportTray(dialogState, new Listener() {
				@Override
				public void handleEvent(Event event) {
					td[0].closeTray();
				}
			});
