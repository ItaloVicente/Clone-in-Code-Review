
			@Override
			protected void updateInfoLabel() {
				super.updateInfoLabel();
				if (infoLabel != null && switchToDialogTriggerSequence != null) {
					infoLabel.setText(
							infoLabel.getText() + ' ' + NLS.bind(QuickAccessMessages.QuickAccess_ShowAsDialog,
									switchToDialogTriggerSequence.format()));
					infoLabel.getParent().layout(true);
				}
			}
