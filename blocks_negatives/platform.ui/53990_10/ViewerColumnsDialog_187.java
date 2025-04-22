		widthText.addVerifyListener(new VerifyListener() {

			@Override
			public void verifyText(VerifyEvent e) {
				if (e.character != 0 && e.keyCode != SWT.BS
						&& e.keyCode != SWT.DEL
						&& !Character.isDigit(e.character)) {
					e.doit = false;
				}
