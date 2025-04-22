				if (text != null && !text.isDisposed()) {
					text.setText(""); //$NON-NLS-1$
					removeAccessibleListener();
				}
				if (shell != null && !shell.isDisposed()) {
					dialogHeight = shell.getSize().y;
					dialogWidth = shell.getSize().x;
					shell.setVisible(false);
				}
