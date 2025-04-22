
			Shell window = getShell();
			Shell shell = window;
			Display display = window.getDisplay();

			Shell[] shells = display.getShells();
			boolean[] enabled = new boolean[shells.length];
			for (int i = 0; i < shells.length; i++) {
				Shell current = shells[i];
				if (current == shell) {
					continue;
				}
				if (current != null && !current.isDisposed()) {
					enabled[i] = current.getEnabled();
					current.setEnabled(false);
				}
			}
			Control currentFocus = display.getFocusControl();

			Menu menuBar = window.getMenuBar();
			boolean menuBarWasEnabled = false;
			if (menuBar != null) {
				menuBarWasEnabled = menuBar.getEnabled();
				menuBar.setEnabled(false);
			}

			final Control[] windowChildren = window.getChildren();
			boolean[] windowChildrenEnabled = new boolean[windowChildren.length];
			for (int i = 0; i < windowChildren.length; i++) {
				if (windowChildren[i] != null && !windowChildren[i].isDisposed()) {
					windowChildrenEnabled[i] = windowChildren[i].isEnabled();
					windowChildren[i].setEnabled(false);
				}
			}

			manager.setCancelEnabled(cancelable);
