		Text text = new Text(getComposite(), SWT.NONE);
		Account account = new Account();
		account.setExpiryDate(new Date());

		Binding b = getDbc().bindValue(SWTObservables.observeText(text, SWT.Modify), BeansObservables.observeValue(account, "expiryDate"));
		Text errorText = new Text(getComposite(), SWT.NONE);

		getDbc().bindValue(SWTObservables.observeText(errorText, SWT.Modify), b.getValidationStatus(), new UpdateValueStrategy(false, UpdateValueStrategy.POLICY_NEVER), null);
		assertTrue(b.getValidationStatus().getValue().isOK());
		enterText(text, "foo");
		assertFalse(b.getValidationStatus().getValue().isOK());
	}
