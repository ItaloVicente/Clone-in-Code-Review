
		registerDisposal();
	}

	private void registerDisposal() {
		addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				prefsMenu.dispose();
				errorBackgroundColor.dispose();
				nextIcon.dispose();
				previousIcon.dispose();
				commitIdIcon.dispose();
				commentsIcon.dispose();
				authorIcon.dispose();
				committerIcon.dispose();
			}
		});
