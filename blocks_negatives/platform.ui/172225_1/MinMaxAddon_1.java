			if (stateElement.getTags().contains(MINIMIZED)) {
				ctf.setMinimizeVisible(false);
				ctf.setMaximizeVisible(true);
				ctf.setMaximized(true);
			} else if (stateElement.getTags().contains(MAXIMIZED)) {
				ctf.setMinimizeVisible(true);
				ctf.setMaximizeVisible(true);
				ctf.setMaximized(true);
			} else {
				ctf.setMinimizeVisible(true);
				ctf.setMaximizeVisible(true);
				ctf.setMinimized(false);
				ctf.setMaximized(false);
				ctf.requestLayout();
			}
