		BrowserManager.getInstance().addObserver(new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				browserIdMap.clear();
			}
		});
