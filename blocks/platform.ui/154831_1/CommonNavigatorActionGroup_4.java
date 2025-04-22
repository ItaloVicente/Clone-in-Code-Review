		frameList.addPropertyChangeListener(event -> {
			if (event.getProperty().equals(FrameList.P_RESET)) {
				upAction.setEnabled(false);
				backAction.setEnabled(false);
				forwardAction.setEnabled(false);

				upAction.update();
