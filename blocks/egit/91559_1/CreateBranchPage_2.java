		nameText.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				e.text = e.text.replaceAll(REGEX_BLANK, UNDERSCORE);
			}
		});
