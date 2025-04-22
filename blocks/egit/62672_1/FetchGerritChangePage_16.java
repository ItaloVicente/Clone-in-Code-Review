		refText.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent event) {
				event.text = event.text.trim();
			}
		});
