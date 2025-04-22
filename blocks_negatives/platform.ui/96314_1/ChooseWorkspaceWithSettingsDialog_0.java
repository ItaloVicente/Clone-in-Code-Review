				if (e.getState() && notExpanded) {
					getShell().setRedraw(false);
					Rectangle shellBounds = getShell().getBounds();
					int entriesToShow = Math.min(4, SettingsTransfer
							.getSettingsTransfers().length);

					shellBounds.height += convertHeightInCharsToPixels(entriesToShow)
							+ IDialogConstants.VERTICAL_SPACING;
					getShell().setBounds(shellBounds);
					getShell().setRedraw(true);
					notExpanded = false;
				}

