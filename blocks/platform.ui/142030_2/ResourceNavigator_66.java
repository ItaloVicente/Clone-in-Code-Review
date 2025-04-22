		updateStatusLine(sel);
		updateActionBars(sel);
		dragDetected = false;
	}

	protected void handleKeyPressed(KeyEvent event) {
		getActionGroup().handleKeyPressed(event);
	}

	protected void handleKeyReleased(KeyEvent event) {
	}

	@Override
