		} else {
			if (keyboardActivationListener != null) {
				viewer.getControl().removeKeyListener(
						keyboardActivationListener);
				keyboardActivationListener = null;
			}
