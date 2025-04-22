		commitText.getTextWidget().addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (UIUtils.isSubmitKeyEvent(event)) {
					okPressed();
				}
			}
		});

