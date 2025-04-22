		commitMessageText.getTextWidget().addVerifyKeyListener(new VerifyKeyListener() {
			@Override
			public void verifyKey(VerifyEvent event) {
				if (UIUtils.isSubmitKeyEvent(event)) {
					event.doit = false;
					commit(false);
				}
			}
		});
