		} else {
			/* Check if there are any other buttons on the button bar.
			 * If not, throw away the button bar composite.  Otherwise
			 * there is an unusually large button bar.
			 */
			if (buttonBar.getChildren().length < 1) {
				buttonBar.dispose();
			}
