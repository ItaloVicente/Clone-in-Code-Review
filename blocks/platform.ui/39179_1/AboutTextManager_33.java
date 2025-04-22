				handCursor.dispose();
				handCursor = null;
				busyCursor.dispose();
				busyCursor = null;
			}
		});
	}

	protected void addListeners() {
		styledText.addMouseListener(new MouseAdapter() {
			@Override
