			shell.layout(true);
			forceLayout(shell);
		 } finally {
			if (shellME.isVisible()) {
				shell.open();
			} else {
				shell.setVisible(false);
			}
		 }
