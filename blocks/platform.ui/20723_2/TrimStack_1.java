				hostPane.addListener(SWT.Traverse, escapeListener);

				hostPane.layout(true);
				hostPane.moveAbove(null);
				hostPane.setVisible(true);
			} else {
				minimizedElement.setVisible(true);
				ctrl.addListener(SWT.Traverse, escapeListener);
			}
