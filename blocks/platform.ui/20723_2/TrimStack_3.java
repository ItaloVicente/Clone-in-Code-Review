				if (hostPane != null && hostPane.isVisible()) {
					hostPane.setVisible(false);
				}
			} else {
				ctrl.removeListener(SWT.Traverse, escapeListener);
				minimizedElement.setVisible(false);
