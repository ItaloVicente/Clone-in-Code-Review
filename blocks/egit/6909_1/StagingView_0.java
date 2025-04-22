		commitMessageText.getTextWidget().addVerifyKeyListener(new VerifyKeyListener() {
			public void verifyKey(VerifyEvent event) {
				if (event.keyCode == SWT.CR
						&& event.stateMask == SWT.CTRL) {
					event.doit = false;
					commit();
				} else if (event.keyCode == SWT.TAB
						&& (event.stateMask & SWT.SHIFT) == 0) {
					event.doit = false;
					authorText.setFocus();
				}
			}
		});

