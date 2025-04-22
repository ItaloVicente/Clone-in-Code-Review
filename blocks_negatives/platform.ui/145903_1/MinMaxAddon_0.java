	private void setCTFButtons(CTabFolder ctf, MUIElement stateElement, boolean hideButtons) {
		if (hideButtons) {
			ctf.setMinimizeVisible(false);
			ctf.setMaximizeVisible(false);
		} else {
			if (stateElement.getTags().contains(MINIMIZED)) {
				ctf.setMinimizeVisible(false);
				ctf.setMaximizeVisible(true);
				ctf.setMaximized(true);
			} else if (stateElement.getTags().contains(MAXIMIZED)) {
				ctf.setMinimizeVisible(true);
				ctf.setMaximizeVisible(true);
