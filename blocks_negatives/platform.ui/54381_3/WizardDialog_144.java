		newShell.addHelpListener(new HelpListener() {
			@Override
			public void helpRequested(HelpEvent event) {
				if (currentPage != null) {
					currentPage.performHelp();
				}
