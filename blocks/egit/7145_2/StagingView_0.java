		commitMessageText.getTextWidget().addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				commitAction.setToolTipText(MessageFormat.format(
						UIText.StagingView_CommitToolTip,
						UIUtils.SUBMIT_KEY_STROKE.format()));
			}

			public void focusLost(FocusEvent e) {
				commitAction.setToolTipText(null);
			}
		});

