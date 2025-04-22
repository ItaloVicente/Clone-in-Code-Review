				@Override
				protected void doClose() {
					txtQuickAccess.setText(""); //$NON-NLS-1$
					resetProviders();
					dialogHeight = shell.getSize().y;
					dialogWidth = shell.getSize().x;
					shell.setVisible(false);
					removeAccessibleListener();
				}
