				for (int i = 0; i < shells.length; i++) {
					Shell current = shells[i];
					if (current == shell) {
						continue;
					}
					if (current != null && !current.isDisposed()) {
						current.setEnabled(enabled[i]);
					}
				}

				for (int i = 0; i < windowChildren.length; i++) {
					if (windowChildren[i] != null && !windowChildren[i].isDisposed()) {
						windowChildren[i].setEnabled(windowChildrenEnabled[i]);
					}
				}
				if (menuBar != null && !menuBar.isDisposed()) {
					menuBar.setEnabled(menuBarWasEnabled);
				}

				if (currentFocus != null && !currentFocus.isDisposed()) {
					currentFocus.forceFocus();
				}
