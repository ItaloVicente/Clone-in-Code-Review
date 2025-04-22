		commitMessageText.getTextWidget().addVerifyKeyListener(new VerifyKeyListener() {
			public void verifyKey(VerifyEvent event) {
				if (UIUtils.isSubmitKeyEvent(event)) {
					event.doit = false;
					commit();
				} else if (event.keyCode == SWT.TAB
						&& (event.stateMask & SWT.SHIFT) == 0) {
					event.doit = false;
					authorText.setFocus();
				}
			}
		});

