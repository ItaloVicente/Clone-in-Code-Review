		getTextWidget().addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent event) {
				int lines = event.text.split("\r\n|\n|\r").length; //$NON-NLS-1$
				if (lines > 1) { // we are pasting, since one cannot type
					event.text = event.text.trim();
				}
			}
		});

